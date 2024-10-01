package hotil.baemo.domains.match.domain.value.exercise;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record ExerciseId(
    @Positive
    @NotNull
    Long id
) {
    public ExerciseId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
