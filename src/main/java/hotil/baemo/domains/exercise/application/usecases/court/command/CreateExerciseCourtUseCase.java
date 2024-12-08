package hotil.baemo.domains.exercise.application.usecases.court.command;

import hotil.baemo.domains.exercise.domain.value.exercise.CourtNumber;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface CreateExerciseCourtUseCase {
    void createExerciseCourt(UserId userId, ExerciseId exerciseId, CourtNumber courtNumber);
}
