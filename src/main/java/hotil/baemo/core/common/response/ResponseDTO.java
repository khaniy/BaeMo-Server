package hotil.baemo.core.common.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder(value = {"code", "payload"})
public class ResponseDTO<T> {

    private final String code;
    private final T payload;

    private ResponseDTO(ResponseCode code, T payload) {
        this.code = code.getCode();
        this.payload = payload;
    }

    public static ResponseDTO<Void> ok() {
        return new ResponseDTO<>(ResponseCode.SUCCESS_OK, null);
    }

    public static <T> ResponseDTO<T> ok(T payload) {
        return new ResponseDTO<>(ResponseCode.SUCCESS_OK, payload);
    }

    public static <T> ResponseDTO<T> ok(ResponseCode code, T payload) {
        return new ResponseDTO<>(code, payload);
    }

    public static ResponseDTO<String> warn(ResponseCode code) {
        return new ResponseDTO<>(code, code.getDescription());
    }

    public static <T> ResponseDTO<T> warn(ResponseCode code, T payload) {return new ResponseDTO<>(code, payload);
    }

    public static ResponseDTO<String> error(ResponseCode code) {
        return new ResponseDTO<>(code, code.getDescription());
    }
}
