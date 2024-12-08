package hotil.baemo.domains.exercise.application.ports.output.score;

import hotil.baemo.domains.exercise.domain.entity.score.ScoreBoard;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;

public interface CommandScoreBoardOutPort {

    void save(ScoreBoard scoreBoard);

    ScoreBoard getScoreBoard(MatchId matchId);

    ScoreBoard getScoreBoardOptional(MatchId matchId);

    void deleteScoreBoard(ScoreBoard scoreBoard);
}
