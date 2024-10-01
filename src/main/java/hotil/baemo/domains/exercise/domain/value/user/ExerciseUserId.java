package hotil.baemo.domains.exercise.domain.value.user;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ExerciseUserId(
    @NotNull
    @Positive
    Long id) {
    public ExerciseUserId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
