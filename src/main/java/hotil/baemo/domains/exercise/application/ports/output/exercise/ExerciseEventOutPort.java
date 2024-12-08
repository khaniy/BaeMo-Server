package hotil.baemo.domains.exercise.application.ports.output.exercise;

import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

import java.util.List;

public interface ExerciseEventOutPort {

    void exerciseCreated(Exercise exercise, UserId userId);

    void exerciseDeleted(Exercise exercise, UserId userId);

    void exerciseDeleted(List<ExerciseId> exerciseIds);

    void exerciseCompleted(List<ExerciseId> exerciseIds);
}
