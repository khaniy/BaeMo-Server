package hotil.baemo.domains.score.application.usecase;

import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.score.TeamPoint;
import hotil.baemo.domains.score.domain.value.user.UserId;

public interface UpdateScoreUseCase {
    void updateScorePoint(MatchId matchId, UserId userId, TeamPoint teamAPoint, TeamPoint teamBPoint);
}
