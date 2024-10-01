package hotil.baemo.domains.exercise.application.usecases.exercise.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseDTO;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RetrieveAllExercisesUseCase {
    List<QExerciseDTO.ExerciseListView> retrieveAllExercises(UserId userId, Pageable pageable);
}
