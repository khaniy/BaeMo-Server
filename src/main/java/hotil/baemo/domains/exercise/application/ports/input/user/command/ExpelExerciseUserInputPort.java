package hotil.baemo.domains.exercise.application.ports.input.user.command;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.application.ports.output.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.ExerciseEventOutPort;
import hotil.baemo.domains.exercise.application.usecases.user.command.ExpelExerciseUserUseCase;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.exercise.domain.roles.ExerciseRule;
import hotil.baemo.domains.exercise.domain.roles.RuleSpecification;
import hotil.baemo.domains.exercise.domain.specification.exercise.command.UpdateExerciseUserSpecification;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@RequiredArgsConstructor
@Service
@Transactional
public class ExpelExerciseUserInputPort implements ExpelExerciseUserUseCase {

    private final CommandExerciseOutputPort commandExercisePort;
    private final RuleSpecification ruleSpecification;
    private final ExerciseEventOutPort exerciseEventOutPort;

    @Override
    public void expelExercise(ExerciseId exerciseId, UserId userId, UserId targetUserId) {
        Exercise exercise = commandExercisePort.getExercise(exerciseId);
        ExerciseRule role = ruleSpecification.getRule(exercise.getExerciseType(), exerciseId, userId);

        ExerciseUser expelledExerciseUser = UpdateExerciseUserSpecification.spec(role, exercise)
            .expelExerciseUser(exercise, targetUserId);

        commandExercisePort.deleteExerciseUser(exerciseId, expelledExerciseUser);
        commandExercisePort.save(exercise);
        exerciseEventOutPort.exerciseUserCancelled(exercise, userId, false);
    }

    @Override
    public void selfExpelExercise(ExerciseId exerciseId, UserId userId) {
        Exercise exercise = commandExercisePort.getExercise(exerciseId);
        if (!EnumSet.of(ExerciseStatus.RECRUITING, ExerciseStatus.RECRUITMENT_FINISHED).contains(exercise.getExerciseStatus())) {
            throw new CustomException(ResponseCode.NOT_ALLOWED_DELETE_EXERCISE_USER);
        }
        ExerciseUser expelledExerciseUser = exercise.expelExerciseUser(userId);
        commandExercisePort.deleteExerciseUser(exerciseId, expelledExerciseUser);
        if (exercise.hasNoParticipants()) {
            commandExercisePort.deleteExercise(exercise);
            exerciseEventOutPort.exerciseDeleted(exercise, userId);
        } else {
            commandExercisePort.save(exercise);
        }
        exerciseEventOutPort.exerciseUserCancelled(exercise, userId, true);
    }
}