package hotil.baemo.domains.exercise.application.ports.input.user.command;

import hotil.baemo.domains.exercise.application.ports.output.exercise.LoadExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.user.CommandExerciseUserOutPort;
import hotil.baemo.domains.exercise.application.ports.output.user.ExerciseUserEventOutPort;
import hotil.baemo.domains.exercise.application.ports.output.user.LoadExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.usecases.user.command.ChangeExerciseUserRoleUseCase;
import hotil.baemo.domains.exercise.domain.policy.user.update.UpdateExerciseMemberRolePolicy;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class ChangeExerciseUserRoleInputPort implements ChangeExerciseUserRoleUseCase {

    private final CommandExerciseUserOutPort commandExerciseUserOutPort;
    private final LoadExerciseOutputPort loadExerciseOutputPort;
    private final LoadExerciseUserOutputPort loadExerciseUserOutputPort;
    private final ExerciseUserEventOutPort exerciseUserEventOutPort;

    @Override
    public void appointUserToAdmin(ExerciseId exerciseId, UserId userId, UserId targetUserId) {
        UpdateExerciseMemberRolePolicy.execute(userId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .get(loadExerciseOutputPort::loadExercise)
            .appointMemberToAdmin(targetUserId)
            .persist(commandExerciseUserOutPort::saveExerciseUser)
            .produce(exerciseUserEventOutPort::exerciseUserRoleChanged);
    }

    @Override
    public void downgradeUserToMember(ExerciseId exerciseId, UserId userId, UserId targetUserId) {
        UpdateExerciseMemberRolePolicy.execute(userId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .get(loadExerciseOutputPort::loadExercise)
            .downgradeAdminToMember(targetUserId)
            .persist(commandExerciseUserOutPort::saveExerciseUser)
            .produce(exerciseUserEventOutPort::exerciseUserRoleChanged);
    }

}