package hotil.baemo.domains.users.domain.value.device;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record DeviceModel(
    @NotBlank
    String model
) {

    public DeviceModel(String model) {
        this.model = model;
        BaemoValueObjectValidator.valid(this);
    }
}
