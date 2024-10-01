package hotil.baemo.domains.score.domain.value.exercise;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ExerciseId(
    @NotNull
    @Positive
    Long id
) {
    public ExerciseId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
