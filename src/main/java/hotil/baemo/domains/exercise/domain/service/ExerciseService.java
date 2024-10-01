package hotil.baemo.domains.exercise.domain.service;

import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseType;

public interface ExerciseService {
    ExerciseType getExerciseType(ExerciseId exerciseId);
}
