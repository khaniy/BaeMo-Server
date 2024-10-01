package hotil.baemo.domains.exercise.application.usecases.user.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseUserDTO;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;

import java.util.List;

public interface RetrieveWaitingUserUseCase {
    List<QExerciseUserDTO.ExerciseUserListView> retrieveWaitingMembers(ExerciseId exerciseId);
}
