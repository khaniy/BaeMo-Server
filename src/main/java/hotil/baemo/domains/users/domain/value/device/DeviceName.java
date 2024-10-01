package hotil.baemo.domains.users.domain.value.device;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record DeviceName(
    @NotBlank
    String name
) {

    public DeviceName(String name) {
        this.name = name;
        BaemoValueObjectValidator.valid(this);
    }
}
