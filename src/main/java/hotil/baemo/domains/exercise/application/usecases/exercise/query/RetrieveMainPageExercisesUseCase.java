package hotil.baemo.domains.exercise.application.usecases.exercise.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseDTO;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

import java.util.List;

public interface RetrieveMainPageExercisesUseCase {
    List<QExerciseDTO.ExerciseListView> retrieveMainExercises(UserId userId);
}
