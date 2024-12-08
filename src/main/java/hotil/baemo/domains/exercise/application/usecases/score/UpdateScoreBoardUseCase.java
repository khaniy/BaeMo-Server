package hotil.baemo.domains.exercise.application.usecases.score;

import hotil.baemo.domains.exercise.domain.entity.user.MatchUser;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

import java.util.List;

public interface UpdateScoreBoardUseCase {

    void scoreTeamA(MatchId matchId, UserId userId);

    void scoreTeamB(MatchId matchId, UserId userId);

    void revertScore(MatchId matchId, UserId userId);

    void updateMatchTeam(MatchId matchId, UserId userId, List<MatchUser> matchUsers);
}
