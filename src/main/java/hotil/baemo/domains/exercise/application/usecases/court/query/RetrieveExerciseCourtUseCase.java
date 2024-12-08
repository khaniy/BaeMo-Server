package hotil.baemo.domains.exercise.application.usecases.court.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseCourtDTO;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

import java.util.List;

public interface RetrieveExerciseCourtUseCase {
    List<QExerciseCourtDTO.ExerciseCourt> retrieveExerciseCourt(UserId userId, ExerciseId exerciseId);
}
