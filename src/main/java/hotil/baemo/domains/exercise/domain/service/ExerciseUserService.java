package hotil.baemo.domains.exercise.domain.service;

import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface ExerciseUserService {

    ExerciseUser getUserForCheckRule(ExerciseId exerciseId, UserId userId);
}
