package hotil.baemo.domains.score.application.usecase;

import hotil.baemo.domains.score.domain.aggregate.MatchUser;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.user.UserId;

import java.util.List;

public interface UpdateScoreBoardUseCase {

    void scoreTeamA(MatchId matchId, UserId userId);

    void scoreTeamB(MatchId matchId, UserId userId);

    void revertScore(MatchId matchId, UserId userId);

    void updateMatchTeam(MatchId matchId, UserId userId, List<MatchUser> matchUsers);
}
