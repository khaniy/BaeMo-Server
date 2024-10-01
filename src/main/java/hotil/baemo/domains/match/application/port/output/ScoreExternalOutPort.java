package hotil.baemo.domains.match.application.port.output;

import hotil.baemo.domains.match.domain.value.match.MatchId;

import java.util.List;

public interface ScoreExternalOutPort {

    void addInitScore(MatchId matchId);

    void deleteScore(MatchId matchId);

    void deleteScores(List<MatchId> matchId);

}
