package hotil.baemo.domains.exercise.domain.value.exercise;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

public record ExerciseTime(
    @NotNull
    ZonedDateTime startTime,
    @NotNull
    ZonedDateTime endTime
) {
    public static final Long MAX_TIME = 6L;
    public static final Long MIN_TIME = 1L;

    public ExerciseTime(ZonedDateTime startTime, ZonedDateTime endTime) {
        if (endTime.isBefore(startTime)) {
            throw new CustomException(ResponseCode.EXERCISE_TIME_01);
        }
//        if (endTime.isAfter(startTime.plusHours(MAX_TIME))) {
//            throw new CustomException(ResponseCode.EXERCISE_TIME_02);
//        }
//        if (endTime.isBefore(startTime.plusHours(MIN_TIME))) {
//            throw new CustomException(ResponseCode.EXERCISE_TIME_03);
//        }

        this.startTime = startTime;
        this.endTime = endTime;
        BaemoValueObjectValidator.valid(this);
    }
}
