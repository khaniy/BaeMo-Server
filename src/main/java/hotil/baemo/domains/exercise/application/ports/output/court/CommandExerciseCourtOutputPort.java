package hotil.baemo.domains.exercise.application.ports.output.court;

import hotil.baemo.domains.exercise.domain.entity.court.ExerciseCourt;
import hotil.baemo.domains.exercise.domain.entity.court.ExerciseCourts;

public interface CommandExerciseCourtOutputPort {
    void save(ExerciseCourts exerciseCourts);

    void save(ExerciseCourt exerciseCourt);

    void delete(ExerciseCourt exerciseCourt);
}
