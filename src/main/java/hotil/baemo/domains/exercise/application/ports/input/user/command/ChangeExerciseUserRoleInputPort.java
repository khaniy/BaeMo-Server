package hotil.baemo.domains.exercise.application.ports.input.user.command;

import hotil.baemo.domains.exercise.application.ports.output.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.ExerciseEventOutPort;
import hotil.baemo.domains.exercise.application.usecases.user.command.ChangeExerciseUserRoleUseCase;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.exercise.domain.roles.ExerciseRule;
import hotil.baemo.domains.exercise.domain.roles.RuleSpecification;
import hotil.baemo.domains.exercise.domain.specification.exercise.command.UpdateExerciseUserSpecification;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class ChangeExerciseUserRoleInputPort implements ChangeExerciseUserRoleUseCase {

    private final CommandExerciseOutputPort commandExercisePort;
    private final ExerciseEventOutPort exerciseEventOutPort;
    private final RuleSpecification ruleSpecification;

    @Override
    public void appointUserToAdmin(ExerciseId exerciseId, UserId userId, UserId targetUserId) {
        Exercise exercise = commandExercisePort.getExercise(exerciseId);
        ExerciseRule rule = ruleSpecification.getRule(exercise.getExerciseType(), exerciseId, userId);

        ExerciseUser user = UpdateExerciseUserSpecification.spec(rule, exercise)
            .appointMemberToAdmin(exercise, targetUserId);
        commandExercisePort.saveExerciseUser(exerciseId, user);
        exerciseEventOutPort.exerciseUserRoleChanged(exercise, userId, user);
    }

    @Override
    public void downgradeUserToMember(ExerciseId exerciseId, UserId userId, UserId targetUserId) {
        Exercise exercise = commandExercisePort.getExercise(exerciseId);
        ExerciseRule rule = ruleSpecification.getRule(exercise.getExerciseType(), exerciseId, userId);

        ExerciseUser user = UpdateExerciseUserSpecification.spec(rule, exercise)
            .downgradeAdminToMember(exercise, targetUserId);
        commandExercisePort.saveExerciseUser(exerciseId, user);
        exerciseEventOutPort.exerciseUserRoleChanged(exercise, userId, user);
    }

}