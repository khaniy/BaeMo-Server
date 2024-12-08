package hotil.baemo.domains.exercise.application.usecases.score;

import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface EndScoreBoardUseCase {
    void stopScoreBoard(MatchId matchId, UserId userId);
}
