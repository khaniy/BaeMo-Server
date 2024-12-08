package hotil.baemo.domains.exercise.domain.value.exercise;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record ExerciseCourtId(
    @Positive
    @NotNull
    Long id
) {
    public ExerciseCourtId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
