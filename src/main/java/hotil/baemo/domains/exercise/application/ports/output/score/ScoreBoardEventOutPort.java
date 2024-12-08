package hotil.baemo.domains.exercise.application.ports.output.score;

import hotil.baemo.domains.exercise.domain.entity.score.ScoreBoard;

public interface ScoreBoardEventOutPort {
    void scoreBoardStopped(ScoreBoard scoreBoard);

    void scoreBoardUpdated(ScoreBoard scoreBoard);
}
