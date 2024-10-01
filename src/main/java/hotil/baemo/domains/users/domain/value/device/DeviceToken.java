package hotil.baemo.domains.users.domain.value.device;

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
