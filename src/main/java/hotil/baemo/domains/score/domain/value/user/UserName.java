package hotil.baemo.domains.score.domain.value.user;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UserName(
    @NotBlank
    String name
) {
    public UserName(String name) {
        this.name = name;
        BaemoValueObjectValidator.valid(this);
    }
}
