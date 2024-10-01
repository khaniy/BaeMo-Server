package hotil.baemo.domains.match.domain.value.exercise;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record ExerciseUserId(
    @Positive
    @NotNull
    Long id
) {
    public ExerciseUserId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
