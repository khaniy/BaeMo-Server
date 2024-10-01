package hotil.baemo.domains.match.domain.spec;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.match.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.match.domain.aggregate.Match;
import hotil.baemo.domains.match.domain.aggregate.MatchOrders;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseUserRole;
import hotil.baemo.domains.match.domain.value.match.MatchStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteMatchSpecification {

    private static final EnumSet<MatchStatus> DELETABLE_STATE = EnumSet.of(MatchStatus.WAITING, MatchStatus.NEXT);

    public static DeleteMatchSpecification spec(ExerciseUser user, MatchStatus matchStatus) {
        if (!Rules.DELETE_RULE.role.test(user.getRole())) {
            throw new CustomException(ResponseCode.IS_NOT_EXERCISE_ADMIN);
        }
        if (!Rules.DELETE_RULE.status.test(matchStatus)) {
            throw new CustomException(ResponseCode.MATCH_IS_NOT_DELETABLE);
        }
        return new DeleteMatchSpecification();
    }

    public MatchOrders deleteMatch(Match match, MatchOrders matchOrders) {
        match.delete();
        return matchOrders.deleteMatchOrder(match.getMatchId());
    }

    @RequiredArgsConstructor
    private enum Rules {
        DELETE_RULE(
            role -> role.equals(ExerciseUserRole.ADMIN),
            DELETABLE_STATE::contains
        );
        private final Predicate<ExerciseUserRole> role;
        private final Predicate<MatchStatus> status;
    }
}
