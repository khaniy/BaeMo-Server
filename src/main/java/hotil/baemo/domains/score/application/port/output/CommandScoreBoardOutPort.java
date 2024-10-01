package hotil.baemo.domains.score.application.port.output;

import hotil.baemo.domains.score.domain.aggregate.ScoreBoard;
import hotil.baemo.domains.score.domain.service.ScoreBoardService;
import hotil.baemo.domains.score.domain.value.match.MatchId;

public interface CommandScoreBoardOutPort extends ScoreBoardService {

    void save(ScoreBoard scoreBoard);

    ScoreBoard getScoreBoard(MatchId matchId);

    void deleteScoreBoard(ScoreBoard scoreBoard);
}
