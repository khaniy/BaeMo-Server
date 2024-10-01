package hotil.baemo.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class BaeMoObjectUtil {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.registerModule(new JavaTimeModule());  // JavaTimeModule 등록
    }

    public static <T> String writeValueAsString(T payload) {
        try {
            return OBJECT_MAPPER.writeValueAsString(payload);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ResponseCode.ETC_ERROR);
        }
    }

    public static <T> T readValue(InputStream src, Class<T> valueType) throws IOException {
        return OBJECT_MAPPER.readValue(src, valueType);
    }

    public static <T> T readValue(String src, Class<T> valueType) {
        try {
            return OBJECT_MAPPER.readValue(src, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ResponseCode.ETC_ERROR);
        }
    }

    public static boolean equalsTo(Object a, Object b) {
        return Objects.equals(a, b);
    }

    public static boolean notEquals(Object a, Object b) {
        return !Objects.equals(a, b);
    }
}