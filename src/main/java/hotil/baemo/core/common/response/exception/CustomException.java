package hotil.baemo.core.common.response.exception;

import hotil.baemo.core.common.response.ResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final ResponseCode responseCode;

    public CustomException(ResponseCode responseCode) {
        super(responseCode.name());
        this.responseCode = responseCode;
    }

    public HttpStatus getHttpStatus() {
        return this.responseCode.getHttpStatus();
    }

    public ResponseCode getCode() {
        return this.responseCode;
    }
}