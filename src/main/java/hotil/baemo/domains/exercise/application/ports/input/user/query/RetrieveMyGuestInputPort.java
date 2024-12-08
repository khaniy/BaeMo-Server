package hotil.baemo.domains.exercise.application.ports.input.user.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseUserDTO;
import hotil.baemo.domains.exercise.application.ports.output.user.RetrieveExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.usecases.user.query.RetrieveMatchUsersUseCase;
import hotil.baemo.domains.exercise.application.usecases.user.query.RetrieveMyGuestUseCase;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrieveMyGuestInputPort implements RetrieveMyGuestUseCase {

    private final RetrieveExerciseUserOutputPort retrieveExerciseUserPort;

    @Override
    public List<QExerciseUserDTO.GuestListView> retrieveMyGuest(UserId userId, ExerciseId exerciseId) {
        return retrieveExerciseUserPort.getMyGuest(userId, exerciseId);
    }
}
