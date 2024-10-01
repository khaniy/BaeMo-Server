package hotil.baemo.core.advice;


import com.fasterxml.jackson.databind.JsonMappingException;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.core.logging.util.ExceptionCollector;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionAdvice {

    private final HttpServletRequest request;
    private final ExceptionCollector exceptionCollector;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDTO<String>> handleApplicationException(CustomException e) {
        exceptionCollector.add(request, e);
        return ResponseEntity
            .status(e.getHttpStatus())
            .body(ResponseDTO.warn(e.getCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseDTO<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        exceptionCollector.add(request, e);
        final var errorMessages = e.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(", "));
        return ResponseDTO.warn(ResponseCode.PARAM_INVALID, errorMessages);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseDTO<String> handleConstraintViolationException(ConstraintViolationException e) {
        exceptionCollector.add(request, e);
        final var errorMessages = e.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(", "));

        e.getConstraintViolations().forEach(violation ->
            log.warn("[ConstraintViolationException] Property: {}, Invalid value: {}, Message: {}",
                violation.getPropertyPath(), violation.getInvalidValue(), violation.getMessage(), e));

        return ResponseDTO.warn(ResponseCode.PARAM_INVALID, errorMessages);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseDTO<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        exceptionCollector.add(request, e);
        return ResponseDTO.warn(ResponseCode.MESSAGE_NOT_READABLE);
    }

    @ExceptionHandler(JsonMappingException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseDTO<String> handleJsonMappingException(JsonMappingException e) {
        exceptionCollector.add(request, e);
        return ResponseDTO.warn(ResponseCode.JSON_INVALID);
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseDTO<String> handleSQLException(SQLException e) {
        exceptionCollector.add(request, e);
        return ResponseDTO.error(ResponseCode.DB_ERROR);
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseDTO<String> handleDataAccessException(DataAccessException e) {
        exceptionCollector.add(request, e);
        return ResponseDTO.error(ResponseCode.DB_ERROR);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseDTO<String> handleException(NoResourceFoundException e) {
        exceptionCollector.add(request, e);
        return ResponseDTO.error(ResponseCode.INVALID_URL);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseDTO<String> handleException(Exception e) {
        exceptionCollector.add(request, e);
        return ResponseDTO.error(ResponseCode.ETC_ERROR);
    }
}
