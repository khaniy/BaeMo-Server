package hotil.baemo.domains.exercise.application.usecases.score;

import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import hotil.baemo.domains.exercise.domain.value.user.UserName;

public interface StartScoreBoardUseCase {
    void startScoreBoard(MatchId matchId, UserId userId, UserName userName);
}
