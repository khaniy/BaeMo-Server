package hotil.baemo.domains.match.domain.spec;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.match.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.match.domain.aggregate.Match;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseUserRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMatchStatusSpecification {

    public static UpdateMatchStatusSpecification spec(ExerciseStatus status, ExerciseUser user) {
        if (!Rules.UPDATE_RULE.exerciseStatus.test(status)) {
            throw new CustomException(ResponseCode.NOW_ALLOWED_UPDATE_MATCH_STATUS);
        }
        if (!Rules.UPDATE_RULE.userRole.test(user.getRole())) {
            throw new CustomException(ResponseCode.IS_NOT_EXERCISE_ADMIN);
        }
        return new UpdateMatchStatusSpecification();
    }

    public void updatePreviousStatus(Match match) {
        match.previousStatus();
    }

    public void updateNextStatus(Match match) {
        match.nextStatus();
    }

    @RequiredArgsConstructor
    private enum Rules {
        UPDATE_RULE(
            status -> status.equals(ExerciseStatus.PROGRESS),
            role -> role.equals(ExerciseUserRole.ADMIN)
        );
        private final Predicate<ExerciseStatus> exerciseStatus;
        private final Predicate<ExerciseUserRole> userRole;
    }
}
