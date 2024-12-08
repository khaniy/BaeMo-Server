package hotil.baemo.domains.exercise.domain.value.exercise;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record LocationCode(
    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$")
    String code
) {
    public LocationCode(String code) {
        this.code = code;
        BaemoValueObjectValidator.valid(this);
    }
}
