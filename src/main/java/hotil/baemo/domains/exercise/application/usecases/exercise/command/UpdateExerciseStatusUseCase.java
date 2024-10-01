package hotil.baemo.domains.exercise.application.usecases.exercise.command;

import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface UpdateExerciseStatusUseCase {

    void completeExercisesFromNow();

    void progressExercisesFromNow();

    void completeExercise(UserId userId, ExerciseId exerciseId);

    void progressExercise(UserId userId, ExerciseId exerciseId);
}
