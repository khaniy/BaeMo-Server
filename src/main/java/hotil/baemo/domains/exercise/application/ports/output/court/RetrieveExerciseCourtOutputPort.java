package hotil.baemo.domains.exercise.application.ports.output.court;

import hotil.baemo.domains.exercise.application.dto.QExerciseCourtDTO;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;

import java.util.List;

public interface RetrieveExerciseCourtOutputPort {
    List<QExerciseCourtDTO.ExerciseCourt> getExerciseCourts(ExerciseId exerciseId);
}
