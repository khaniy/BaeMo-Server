package hotil.baemo.domains.score.domain.spec;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.score.domain.value.score.ScoreBoardRole;
import hotil.baemo.domains.score.domain.aggregate.ScoreBoard;
import hotil.baemo.domains.score.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectScoreBoardSpecification {

    private static final EnumSet<ScoreBoardRole> AVAILABLE_ROLES = EnumSet.of(ScoreBoardRole.REFEREE, ScoreBoardRole.PARTICIPANT, ScoreBoardRole.ADMIN);

    public static ConnectScoreBoardSpecification spec(ScoreBoardRole role) {
        if (!Rules.RULE.actorRule.test(role)) {
            throw new CustomException(ResponseCode.IS_NOT_PARTICIPATE_MEMBER);
        }
        return new ConnectScoreBoardSpecification();
    }

    public void subscribeScoreBoard(
        UserId userId,
        ScoreBoard scoreBoard
    ) {
        scoreBoard.subscribe(userId);
    }

    public void unsubscribeScoreBoard(
        UserId userId,
        ScoreBoard scoreBoard
    ) {
        scoreBoard.unsubscribe(userId);
    }

    @RequiredArgsConstructor
    private enum Rules {
        RULE(
            AVAILABLE_ROLES::contains
        );
        private final Predicate<ScoreBoardRole> actorRule;
    }
}
