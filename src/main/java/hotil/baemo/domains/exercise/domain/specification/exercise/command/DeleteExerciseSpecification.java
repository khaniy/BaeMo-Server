package hotil.baemo.domains.exercise.domain.specification.exercise.command;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.roles.ExerciseRule;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteExerciseSpecification {

    private final ExerciseRule exerciseRule;

    public static DeleteExerciseSpecification spec(ExerciseRule rule, Exercise exercise) {
        if (!Rules.DELETE_RULE.actorRule.test(rule)) {
            throw new CustomException(ResponseCode.EXERCISE_ROLE_AUTH_FAILED);
        }
        if (!Rules.DELETE_RULE.statusRule.test(exercise.getExerciseStatus())) {
            throw new CustomException(ResponseCode.NOT_ALLOWED_DELETE_EXERCISE);
        }
        return new DeleteExerciseSpecification(rule);
    }

    public void delete(Exercise exercise) {
        exercise.delete();
    }

    @RequiredArgsConstructor
    private enum Rules {
        DELETE_RULE(
            ExerciseRule::isAdminRule,
            status -> EnumSet.of(ExerciseStatus.RECRUITING, ExerciseStatus.RECRUITMENT_FINISHED).contains(status)
        );
        private final Predicate<ExerciseRule> actorRule;
        private final Predicate<ExerciseStatus> statusRule;
    }
}
