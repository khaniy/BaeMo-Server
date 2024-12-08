package hotil.baemo.domains.exercise.application.ports.input.user.command;

import hotil.baemo.domains.exercise.application.ports.output.exercise.LoadExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.user.CommandExerciseUserOutPort;
import hotil.baemo.domains.exercise.application.ports.output.user.LoadExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.usecases.user.command.RejectPendingUserUseCase;
import hotil.baemo.domains.exercise.domain.policy.user.delete.RejectPendingGuestPolicy;
import hotil.baemo.domains.exercise.domain.policy.user.delete.RejectPendingUserPolicy;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RejectPendingUserInputPort implements RejectPendingUserUseCase {

    private final CommandExerciseUserOutPort commandExerciseUserOutPort;
    private final LoadExerciseOutputPort loadExerciseOutputPort;
    private final LoadExerciseUserOutputPort loadExerciseUserOutputPort;

    @Override
    public void rejectPendingMember(ExerciseId exerciseId, UserId userId, UserId targetUserId) {
        RejectPendingUserPolicy.execute(userId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .get(loadExerciseOutputPort::loadExercise)
            .rejectPendingUser(targetUserId)
            .persist(commandExerciseUserOutPort::deleteUser);
    }

    @Override
    public void rejectPendingGuest(ExerciseId exerciseId, UserId userId, UserId targetUserId) {
        RejectPendingGuestPolicy.execute(userId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .get(loadExerciseOutputPort::loadExercise)
            .rejectPendingUser(targetUserId)
            .persist(commandExerciseUserOutPort::deleteUser);
    }
}
