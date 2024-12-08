package hotil.baemo.domains.exercise.application.ports.output.exercise;

import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseThumbnail;

import java.util.List;

public interface CommandExerciseOutputPort {

    Exercise save(Exercise exercise);

    Exercise save(Exercise exercise, ExerciseThumbnail thumbnail);

    void saveAll(List<Exercise> exercises);

    void delete(Exercise exercise);

    void deleteAll(List<Exercise> exercises);
}
