package hotil.baemo.domains.exercise.domain.value.exercise;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CourtNumber(
    @NotNull
    @Positive
    Integer number
) {
    public CourtNumber(Integer number) {
        this.number = number;
        BaemoValueObjectValidator.valid(this);
    }
}
