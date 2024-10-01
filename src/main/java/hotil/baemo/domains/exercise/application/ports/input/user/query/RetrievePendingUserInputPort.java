package hotil.baemo.domains.exercise.application.ports.input.user.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseUserDTO;
import hotil.baemo.domains.exercise.application.ports.output.RetrieveExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.usecases.user.query.RetrievePendingUserUseCase;
import hotil.baemo.domains.exercise.domain.roles.ExerciseRule;
import hotil.baemo.domains.exercise.domain.roles.RuleSpecification;
import hotil.baemo.domains.exercise.domain.specification.exercise.query.RetrieveExerciseUserSpecification;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrievePendingUserInputPort implements RetrievePendingUserUseCase {

    private final RetrieveExerciseUserOutputPort retrieveExerciseUserPort;
    private final RuleSpecification ruleSpecification;

    @Override
    public List<QExerciseUserDTO.ExerciseUserListView> retrievePendingMembers(ExerciseId exerciseId, UserId userId) {
        ExerciseRule exerciseRule = ruleSpecification.getRule(exerciseId, userId);
        RetrieveExerciseUserSpecification.spec(exerciseRule).retrievePendingUsers();
        return retrieveExerciseUserPort.getPendingMembers(exerciseId);
    }

    @Override
    public List<QExerciseUserDTO.ExerciseUserListView> retrievePendingGuests(ExerciseId exerciseId, UserId retrievalId) {
        ExerciseRule exerciseRule = ruleSpecification.getRule(exerciseId, retrievalId);
        RetrieveExerciseUserSpecification.spec(exerciseRule).retrievePendingGuestUsers();
        return retrieveExerciseUserPort.getPendingGuests(exerciseId);
    }
}
