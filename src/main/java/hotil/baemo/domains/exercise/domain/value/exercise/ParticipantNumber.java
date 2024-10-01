package hotil.baemo.domains.exercise.domain.value.exercise;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ParticipantNumber(
        @Min(0)
        @Max(100)
        @NotNull
        Integer number
) {
    public static Integer MIN_PARTICIPANT = 4;
    public ParticipantNumber(Integer number) {
        this.number = number;
        BaemoValueObjectValidator.valid(this);
    }
}
