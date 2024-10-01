package hotil.baemo.domains.users.domain.value.device;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record DeviceBrand(
    @NotBlank
    String brand
) {

    public DeviceBrand(String brand) {
        this.brand = brand;
        BaemoValueObjectValidator.valid(this);
    }
}
