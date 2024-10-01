package hotil.baemo.domains.exercise.application.usecases.exercise.command;

import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface DeleteExerciseUseCase {
    void deleteExercise(ExerciseId exerciseId, UserId userId);
}
