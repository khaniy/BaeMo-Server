package hotil.baemo.domains.notification.domains.value.notification;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DeviceToken(
    @NotNull
    @Positive
    Long userId,
    @NotBlank
    String token
) {

    public DeviceToken(Long userId, String token) {
        this.userId = userId;
        this.token = token;
        BaemoValueObjectValidator.valid(this);
    }
}
