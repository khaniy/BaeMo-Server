package hotil.baemo.domains.notification.domains.value.exercise;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

public record ExerciseTime(
    @NotNull
    ZonedDateTime startTime,
    @NotNull
    ZonedDateTime endTime
) {
    public ExerciseTime(ZonedDateTime startTime, ZonedDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        BaemoValueObjectValidator.valid(this);
    }
}
