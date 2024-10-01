package hotil.baemo.domains.exercise.application.ports.input.user.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseUserDTO;
import hotil.baemo.domains.exercise.application.ports.output.RetrieveExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.usecases.user.query.RetrieveParticipatedUserUseCase;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrieveParticipatedUserInputPort implements RetrieveParticipatedUserUseCase {

    private final RetrieveExerciseUserOutputPort retrieveExerciseUserPort;

    @Override
    public List<QExerciseUserDTO.ExerciseUserListView> retrieveParticipatedMembers(ExerciseId exerciseId) {
        return retrieveExerciseUserPort.getParticipatedMembers(exerciseId);
    }
}
