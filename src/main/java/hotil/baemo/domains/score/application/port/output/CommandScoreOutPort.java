package hotil.baemo.domains.score.application.port.output;

import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.aggregate.Score;

public interface CommandScoreOutPort {
    Score getScore(MatchId matchId);
    void addInitScore(Score score);
    void deleteScore(MatchId matchId);
    void saveScore(Score score);
}
