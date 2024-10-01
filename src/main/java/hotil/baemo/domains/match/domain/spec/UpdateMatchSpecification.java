package hotil.baemo.domains.match.domain.spec;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.match.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.match.domain.aggregate.Match;
import hotil.baemo.domains.match.domain.aggregate.MatchOrders;
import hotil.baemo.domains.match.domain.aggregate.MatchUsers;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseUserRole;
import hotil.baemo.domains.match.domain.value.match.CourtNumber;
import hotil.baemo.domains.match.domain.value.match.MatchStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMatchSpecification {

    private static final EnumSet<MatchStatus> EDITABLE_STATE = EnumSet.of(MatchStatus.WAITING, MatchStatus.NEXT, MatchStatus.COMPLETE);

    public static UpdateMatchSpecification spec(ExerciseUser user, Match match, List<ExerciseUser> participatedUsers, MatchUsers matchUsers) {
        if (!Rules.UPDATE_RULE.userRole.test(user.getRole())) {
            throw new CustomException(ResponseCode.IS_NOT_EXERCISE_ADMIN);
        }
        if (!Rules.UPDATE_RULE.matchStatus.test(match.getMatchStatus())) {
            throw new CustomException(ResponseCode.NOW_ALLOWED_UPDATE_MATCH);
        }
        if (!matchUsers.isParticipatedExerciseUsers(participatedUsers)) {
            throw new CustomException(ResponseCode.IS_NOT_PARTICIPATE_MEMBER);
        }
        return new UpdateMatchSpecification();
    }

    public void updateMatch(Match match, CourtNumber courtNumber, MatchUsers matchUsers) {
        match.updateMatch(matchUsers, courtNumber);
    }

    @RequiredArgsConstructor
    private enum Rules {
        UPDATE_RULE(
            EDITABLE_STATE::contains,
            role -> role.equals(ExerciseUserRole.ADMIN)
        );
        private final Predicate<MatchStatus> matchStatus;
        private final Predicate<ExerciseUserRole> userRole;
    }
}
