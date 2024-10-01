package hotil.baemo.domains.users.domain.value.device;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record DeviceType(
    @NotBlank
    String type
) {

    public DeviceType(String type) {
        this.type = type;
        BaemoValueObjectValidator.valid(this);
    }
}
