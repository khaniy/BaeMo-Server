package hotil.baemo.domains.exercise.application.usecases.user.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseUserDTO;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

import java.util.List;

public interface RetrieveMyGuestUseCase {
    List<QExerciseUserDTO.GuestListView> retrieveMyGuest(UserId userId, ExerciseId exerciseId);
}
