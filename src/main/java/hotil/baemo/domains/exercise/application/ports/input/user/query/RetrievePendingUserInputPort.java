package hotil.baemo.domains.exercise.application.ports.input.user.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseUserDTO;
import hotil.baemo.domains.exercise.application.ports.output.exercise.LoadExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.user.LoadExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.user.RetrieveExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.usecases.user.query.RetrievePendingUserUseCase;
import hotil.baemo.domains.exercise.domain.policy.user.retrieve.RetrievePendingExerciseUserPolicy;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrievePendingUserInputPort implements RetrievePendingUserUseCase {

    private final RetrieveExerciseUserOutputPort retrieveExerciseUserPort;
    private final LoadExerciseUserOutputPort loadExerciseUserOutputPort;

    @Override
    public List<QExerciseUserDTO.ExerciseUserListView> retrievePendingMembers(ExerciseId exerciseId, UserId userId) {
        return RetrievePendingExerciseUserPolicy.execute(userId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .get(retrieveExerciseUserPort::getPendingMembers);
    }

    @Override
    public List<QExerciseUserDTO.ExerciseUserListView> retrievePendingGuests(ExerciseId exerciseId, UserId retrievalId) {
        return RetrievePendingExerciseUserPolicy.execute(retrievalId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .get(retrieveExerciseUserPort::getPendingGuests);
    }
}
