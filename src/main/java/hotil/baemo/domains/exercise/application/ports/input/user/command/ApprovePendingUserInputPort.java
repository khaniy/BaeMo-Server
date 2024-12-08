package hotil.baemo.domains.exercise.application.ports.input.user.command;

import hotil.baemo.domains.exercise.application.ports.output.exercise.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.exercise.LoadExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.user.ExerciseUserEventOutPort;
import hotil.baemo.domains.exercise.application.ports.output.user.LoadExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.usecases.user.command.ApprovePendingUserUseCase;
import hotil.baemo.domains.exercise.domain.policy.user.update.ApprovePendingGuestPolicy;
import hotil.baemo.domains.exercise.domain.policy.user.update.ApprovePendingUserPolicy;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovePendingUserInputPort implements ApprovePendingUserUseCase {

    private final CommandExerciseOutputPort commandExerciseOutputPort;
    private final LoadExerciseUserOutputPort loadExerciseUserOutputPort;
    private final LoadExerciseOutputPort loadExerciseOutputPort;
    private final ExerciseUserEventOutPort exerciseUserEventOutPort;

    @Override
    public void approvePendingMember(ExerciseId exerciseId, UserId userId, UserId targetUserId) {
        ApprovePendingUserPolicy.execute(userId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .get(loadExerciseOutputPort::loadExercise)
            .approvePendingUser(targetUserId)
            .persist(commandExerciseOutputPort::save)
            .produce(
                exerciseUserEventOutPort::exerciseUserApproved,
                exerciseUserEventOutPort::exerciseUserParticipated
            );
    }

    @Override
    public void approvePendingGuest(ExerciseId exerciseId, UserId userId, UserId targetUserId) {
        ApprovePendingGuestPolicy.execute(userId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .get(loadExerciseOutputPort::loadClubExercise)
            .approvePendingGuest(targetUserId)
            .persist(commandExerciseOutputPort::save)
            .produce(
                exerciseUserEventOutPort::exerciseUserApproved,
                exerciseUserEventOutPort::exerciseUserParticipated
            );
    }
}
