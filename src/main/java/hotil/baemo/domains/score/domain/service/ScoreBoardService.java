package hotil.baemo.domains.score.domain.service;

import hotil.baemo.domains.score.domain.aggregate.ScoreBoard;
import hotil.baemo.domains.score.domain.value.match.MatchId;

public interface ScoreBoardService {
    ScoreBoard getScoreBoardOptional(MatchId matchId);
}
