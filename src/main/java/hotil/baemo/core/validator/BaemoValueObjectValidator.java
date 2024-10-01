package hotil.baemo.core.validator;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public final class BaemoValueObjectValidator {

    private static final Validator VALIDATOR;

    static {
        VALIDATOR = Validation
            .buildDefaultValidatorFactory()
            .getValidator();
    }

    public static <T> void valid(final T vo) {
        final var violations = VALIDATOR.validate(vo);

        if (!violations.isEmpty()) {
            final var message = new StringBuilder();
            violations.forEach(v -> message.append(v.getMessage()).append(System.lineSeparator()));
            // TODO : log
            System.out.println(vo.toString());
            System.out.println(message);
            throw new CustomException(ResponseCode.INVALID_VALUE);
        }
    }
}