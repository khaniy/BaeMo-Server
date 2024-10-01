package hotil.baemo.domains.score.domain.spec;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.score.domain.aggregate.Match;
import hotil.baemo.domains.score.domain.aggregate.Score;
import hotil.baemo.domains.score.domain.aggregate.ScoreBoard;
import hotil.baemo.domains.score.domain.value.match.MatchStatus;
import hotil.baemo.domains.score.domain.value.score.ScoreBoardRole;
import hotil.baemo.domains.score.domain.value.user.UserId;
import hotil.baemo.domains.score.domain.value.user.UserName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StartScoreBoardSpecification {

    private static final EnumSet<ScoreBoardRole> AVAILABLE_ROLE = EnumSet.of(ScoreBoardRole.PARTICIPANT, ScoreBoardRole.ADMIN);
    private static final MatchStatus AVAILABLE_STATUS = MatchStatus.PROGRESS;

    public static StartScoreBoardSpecification spec(ScoreBoardRole role, Match match) {
        if (!Rules.RULE.actorRule.test(role)) {
            throw new CustomException(ResponseCode.IS_NOT_PARTICIPATE_MEMBER);
        }
        if (!Rules.RULE.matchRule.test(match.status())) {
            throw new CustomException(ResponseCode.IS_NOT_PROGRESS_MATCH);
        }
//        if (!match.isTeamDefined()) {
//            throw new CustomException(ResponseCode.EXERCISE_COMPLETED);
//        }
        return new StartScoreBoardSpecification();
    }

    public ScoreBoard startScoreBoard(UserId refereeId, UserName refereeName, Score score) {
        return ScoreBoard.initScoreBoard(refereeId, refereeName, score);
    }

    @RequiredArgsConstructor
    private enum Rules {
        RULE(
            AVAILABLE_ROLE::contains,
            AVAILABLE_STATUS::equals
        );
        private final Predicate<ScoreBoardRole> actorRule;
        private final Predicate<MatchStatus> matchRule;
    }
}
