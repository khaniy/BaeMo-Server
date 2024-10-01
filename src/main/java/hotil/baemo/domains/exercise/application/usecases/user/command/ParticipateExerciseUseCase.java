package hotil.baemo.domains.exercise.application.usecases.user.command;

import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface ParticipateExerciseUseCase {

    void participateExercise(ExerciseId exerciseId, UserId userId);
}
