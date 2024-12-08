package hotil.baemo.domains.users.domain.value.information;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record Location(
    @NotBlank
    String location,

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$")
    String code
) {
    public Location(String location, String code) {
        this.location = location;
        this.code = code;
        BaemoValueObjectValidator.valid(this);
    }
}
