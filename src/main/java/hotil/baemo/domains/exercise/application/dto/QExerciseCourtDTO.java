package hotil.baemo.domains.exercise.application.dto;

import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;

public interface QExerciseCourtDTO {
    record ExerciseCourt(
        Long courtId,
        Long exerciseId,
        Integer courtNumber,
        Boolean isProgress
    ) implements QExerciseCourtDTO {
    }
}
