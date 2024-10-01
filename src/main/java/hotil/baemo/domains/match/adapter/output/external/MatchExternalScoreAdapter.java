package hotil.baemo.domains.match.adapter.output.external;

import hotil.baemo.domains.match.application.port.output.ScoreExternalOutPort;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.score.adapter.input.internal.ScoreBoardInternalAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchExternalScoreAdapter implements ScoreExternalOutPort {

    private final ScoreBoardInternalAdapter scoreBoardInternalAdapter;

    @Override
    public void addInitScore(MatchId matchId) {
        scoreBoardInternalAdapter.addInitScore(matchId.id());
    }

    @Override
    public void deleteScore(MatchId matchId) {
        scoreBoardInternalAdapter.deleteScore(matchId.id());
    }

    @Override
    public void deleteScores(List<MatchId> matchIds) {
        scoreBoardInternalAdapter.deleteScores(matchIds.stream().map(MatchId::id).collect(Collectors.toList()));
    }
}
