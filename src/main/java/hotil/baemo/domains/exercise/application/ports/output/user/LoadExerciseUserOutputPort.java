package hotil.baemo.domains.exercise.application.ports.output.user;

import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface LoadExerciseUserOutputPort {

    ExerciseUser loadExerciseUser(ExerciseId exerciseId, UserId userId);
}
