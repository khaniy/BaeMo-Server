package hotil.baemo.domains.exercise.application.usecases.exercise.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseDTO;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

import java.util.List;

public interface RetrieveUserExercisesUseCase {

    List<QExerciseDTO.ExerciseListView> retrieveUserExercises(UserId userId);


}
