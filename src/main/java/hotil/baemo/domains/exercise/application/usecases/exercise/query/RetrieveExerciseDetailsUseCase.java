package hotil.baemo.domains.exercise.application.usecases.exercise.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseDTO;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface RetrieveExerciseDetailsUseCase {
    QExerciseDTO.ExerciseDetailViewWithAuth retrieveDetails(UserId userId, ExerciseId exerciseId);
}
