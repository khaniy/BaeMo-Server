package hotil.baemo.domains.exercise.domain.specification.exercise.query;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.roles.ExerciseRule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveExerciseUserSpecification {

    private final ExerciseRule rule;

    public static RetrieveExerciseUserSpecification spec(ExerciseRule rule) {
        return new RetrieveExerciseUserSpecification(rule);
    }

    public void retrievePendingGuestUsers() {
        if (!Rules.ADMIN_RULE.actorRule.test(rule)) {
            throw new CustomException(ResponseCode.EXERCISE_ROLE_AUTH_FAILED);
        }
    }

    public void retrievePendingUsers() {
        if (!Rules.ADMIN_RULE.actorRule.test(rule)) {
            throw new CustomException(ResponseCode.EXERCISE_ROLE_AUTH_FAILED);
        }
    }


    @RequiredArgsConstructor
    private enum Rules {
        ADMIN_RULE(ExerciseRule::isAdminRule);

        private final Predicate<ExerciseRule> actorRule;
    }
}
