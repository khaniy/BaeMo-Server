package hotil.baemo.core.validator;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaemoValidator<T> {

    private static final Validator VALIDATOR;

    static {
        var factory = Validation.buildDefaultValidatorFactory();
        VALIDATOR = factory.getValidator();
    }

    protected void valid() {
        final var violations = VALIDATOR.validate((T) this);

        if (!violations.isEmpty()) {
            final var message = new StringBuilder();
            violations.forEach(v -> message.append(v.getMessage()).append(System.lineSeparator()));
            // TODO : log
            log.error(this.toString());
            log.error(String.valueOf(message));
            throw new CustomException(ResponseCode.CRITIC_ERROR);
        }
    }
}