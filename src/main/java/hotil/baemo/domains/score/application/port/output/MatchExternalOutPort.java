package hotil.baemo.domains.score.application.port.output;

import hotil.baemo.domains.score.domain.aggregate.Match;
import hotil.baemo.domains.score.domain.aggregate.MatchUser;
import hotil.baemo.domains.score.domain.service.MatchService;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.match.MatchStatus;

import java.util.List;

public interface MatchExternalOutPort extends MatchService {

    void updateMatchStatus(MatchId matchId, MatchStatus status);

    Match getMatch(MatchId matchId);

    void saveMatchUsers(MatchId matchId, List<MatchUser> matchUsers);
}
