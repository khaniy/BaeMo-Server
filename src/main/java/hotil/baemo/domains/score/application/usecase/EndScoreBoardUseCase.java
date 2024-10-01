package hotil.baemo.domains.score.application.usecase;

import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.user.UserId;

public interface EndScoreBoardUseCase {
    void stopScoreBoard(MatchId matchId, UserId userId);
}
