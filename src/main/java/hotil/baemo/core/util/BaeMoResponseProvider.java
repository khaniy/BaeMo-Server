package hotil.baemo.core.util;

import hotil.baemo.core.common.response.ResponseCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;

public class BaeMoResponseProvider {
    private final HttpServletResponse response;

    private String responseDTO;
    private HttpStatus httpStatus;

    private BaeMoResponseProvider(HttpServletResponse response) {
        this.response = response;
    }

    public static BaeMoResponseProvider setUpHttpServletResponse(HttpServletResponse response) {
        return new BaeMoResponseProvider(response);
    }

    public <T> BaeMoResponseProvider responseDTO(T responseDTO) {
        this.responseDTO = BaeMoObjectUtil.writeValueAsString(responseDTO);
        return this;
    }

    public BaeMoResponseProvider status(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public BaeMoResponseProvider status(ResponseCode code) {
        this.httpStatus = code.getHttpStatus();
        return this;
    }

    public BaeMoResponseProvider header(String key, String value) {
        this.response.setHeader(key, value);
        return this;
    }

    public void complete() {
        try {
            response.setContentType("application/json; charset=UTF-8");

            response.setCharacterEncoding(UTF_8.name());
            response.setStatus(httpStatus.value() == 0 ? 200 : httpStatus.value());
            response.setContentLength(responseDTO.getBytes(StandardCharsets.UTF_8).length);

            response.getWriter().write(responseDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}