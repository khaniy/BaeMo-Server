package hotil.baemo.domains.score.domain.spec;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.score.domain.aggregate.Score;
import hotil.baemo.domains.score.domain.aggregate.ScoreBoard;
import hotil.baemo.domains.score.domain.value.score.ScoreBoardRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StopScoreBoardSpecification {

    private static final EnumSet<ScoreBoardRole> AVAILABLE_ROLE = EnumSet.of(ScoreBoardRole.REFEREE, ScoreBoardRole.ADMIN);

    public static StopScoreBoardSpecification spec(ScoreBoardRole role) {
        if (!Rules.RULE.actorRule.test(role)) {
            throw new CustomException(ResponseCode.ONLY_REFEREE_CAN_STOP);
        }
        return new StopScoreBoardSpecification();
    }

    public Score endScoreBoard(ScoreBoard scoreBoard, Score score) {
        score.update(
            scoreBoard.getScore().getTeamAPointLog(),
            scoreBoard.getScore().getTeamBPointLog(),
            scoreBoard.getScore().getTeamAPoint(),
            scoreBoard.getScore().getTeamBPoint(),
            scoreBoard.getScore().getScoreLog());
        return score;
    }


    @RequiredArgsConstructor
    private enum Rules {
        RULE(AVAILABLE_ROLE::contains);
        private final Predicate<ScoreBoardRole> actorRule;
    }
}
