package hotil.baemo.domains.score.application.usecase;

import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.user.UserId;
import hotil.baemo.domains.score.domain.value.user.UserName;

public interface StartScoreBoardUseCase {
    void startScoreBoard(MatchId matchId, UserId userId, UserName userName);
}
