package hotil.baemo.domains.score.domain.spec;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.score.domain.value.score.ScoreBoardRole;
import hotil.baemo.domains.score.domain.aggregate.ScoreBoard;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateScoreBoardSpecification {

    private static final ScoreBoardRole AVAILABLE_ROLE = ScoreBoardRole.REFEREE;

    public static UpdateScoreBoardSpecification spec(ScoreBoardRole role) {
        if (!Rules.CREATE_RULE.actorRule.test(role)) {
            throw new CustomException(ResponseCode.ONLY_REFEREE_CAN_UPDATE);
        }
        return new UpdateScoreBoardSpecification();
    }

    public void scoreTeamA(ScoreBoard scoreBoard) {
        scoreBoard.scoreTeamA();
    }
    public void scoreTeamB(ScoreBoard scoreBoard) {
        scoreBoard.scoreTeamB();
    }
    public void revertScore(ScoreBoard scoreBoard) {
        scoreBoard.revertScore();
    }

    @RequiredArgsConstructor
    private enum Rules {
        CREATE_RULE(role->role.equals(AVAILABLE_ROLE));
        private final Predicate<ScoreBoardRole> actorRule;
    }
}
