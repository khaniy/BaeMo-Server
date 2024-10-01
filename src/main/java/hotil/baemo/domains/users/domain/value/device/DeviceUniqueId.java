package hotil.baemo.domains.users.domain.value.device;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record DeviceUniqueId(
    @NotBlank
    String id
) {

    public DeviceUniqueId(String id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
