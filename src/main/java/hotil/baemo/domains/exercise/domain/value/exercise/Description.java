package hotil.baemo.domains.exercise.domain.value.exercise;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record Description(
    @NotNull
    @Length(max = 500)
    String description
) {
    public Description(String description) {
        this.description = description;
        BaemoValueObjectValidator.valid(this);
    }
}
