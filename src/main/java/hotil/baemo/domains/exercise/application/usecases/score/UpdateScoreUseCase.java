package hotil.baemo.domains.exercise.application.usecases.score;

import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.score.TeamPoint;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface UpdateScoreUseCase {
    void updateScorePoint(MatchId matchId, UserId userId, TeamPoint teamAPoint, TeamPoint teamBPoint);
}
