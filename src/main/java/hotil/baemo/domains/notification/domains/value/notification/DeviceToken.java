package hotil.baemo.domains.notification.domains.value.notification;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record DeviceToken(
    @NotBlank
    String token
) {

    public DeviceToken(String token) {
        this.token = token;
        BaemoValueObjectValidator.valid(this);
    }
}
