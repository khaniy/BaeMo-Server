package hotil.baemo.domains.match.domain.spec;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.match.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.match.domain.aggregate.Match;
import hotil.baemo.domains.match.domain.aggregate.MatchOrders;
import hotil.baemo.domains.match.domain.aggregate.MatchUsers;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseUserRole;
import hotil.baemo.domains.match.domain.value.match.CourtNumber;
import hotil.baemo.domains.match.domain.value.match.Order;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateMatchSpecification {

    public static CreateMatchSpecification spec(
        ExerciseUser user,
        ExerciseStatus exerciseStatus,
        List<ExerciseUser> participatedUsers,
        MatchUsers matchUsers
    ) {
        if (!Rules.CREATE_RULE.exerciseStatus.test(exerciseStatus)) {
            throw new CustomException(ResponseCode.EXERCISE_COMPLETED);
        }
        if (!Rules.CREATE_RULE.userRole.test(user.getRole())) {
            throw new CustomException(ResponseCode.IS_NOT_EXERCISE_ADMIN);
        }
        if (!matchUsers.isParticipatedExerciseUsers(participatedUsers)){
            throw new CustomException(ResponseCode.IS_NOT_PARTICIPATE_MEMBER);
        }
        return new CreateMatchSpecification();
    }

    public Match createMatch(ExerciseId exerciseId, CourtNumber courtNumber, MatchUsers matchUsers, MatchOrders matchOrders) {
        Order newMatchOrder = matchOrders.getNewMatchOrder();
        return Match.init(exerciseId, courtNumber, matchUsers, newMatchOrder);
    }

    @RequiredArgsConstructor
    private enum Rules {
        CREATE_RULE(
            status-> !status.equals(ExerciseStatus.COMPLETE),
            role -> role.equals(ExerciseUserRole.ADMIN)
        );
        private final Predicate<ExerciseStatus> exerciseStatus;
        private final Predicate<ExerciseUserRole> userRole;
    }
}
