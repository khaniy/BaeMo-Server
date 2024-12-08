package hotil.baemo.domains.exercise.application.ports.output.court;

import hotil.baemo.domains.exercise.domain.entity.court.ExerciseCourt;
import hotil.baemo.domains.exercise.domain.entity.court.ExerciseCourts;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseCourtId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;

public interface LoadExerciseCourtOutputPort {
    ExerciseCourts loadExerciseCourts(ExerciseId exerciseId);

    ExerciseCourt loadExerciseCourt(ExerciseCourtId exerciseCourtId);
}
