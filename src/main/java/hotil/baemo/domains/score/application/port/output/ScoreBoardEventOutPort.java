package hotil.baemo.domains.score.application.port.output;

import hotil.baemo.domains.score.domain.aggregate.ScoreBoard;

public interface ScoreBoardEventOutPort {
    void scoreBoardStopped(ScoreBoard scoreBoard);

    void scoreBoardUpdated(ScoreBoard scoreBoard);
}
