package hotil.baemo.domains.exercise.domain.specification.exercise.command;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.roles.ExerciseRule;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateExerciseStatusSpecification {

    public static UpdateExerciseStatusSpecification spec(Exercise exercise, ExerciseRule role) {
        if (!Rules.RULE.actor.test(role)) {
            throw new CustomException(ResponseCode.EXERCISE_ROLE_AUTH_FAILED);
        }
        if (!Rules.RULE.status.test(exercise.getExerciseStatus())) {
            throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_EXERCISE);
        }

        return new UpdateExerciseStatusSpecification();
    }

    public void progressExercise(Exercise exercise) {
        exercise.progress();
    }

    public void completeExercise(Exercise exercise) {
        exercise.complete();
    }

    @RequiredArgsConstructor
    private enum Rules {
        RULE(
            ExerciseRule::isAdminRule,
            status -> !status.equals(ExerciseStatus.COMPLETE)
        );
        private final Predicate<ExerciseRule> actor;
        private final Predicate<ExerciseStatus> status;
    }
}
