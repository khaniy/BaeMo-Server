package hotil.baemo.domains.score.adapter.input.internal;

import hotil.baemo.domains.score.adapter.output.persistence.mapper.ScoreEntityMapper;
import hotil.baemo.domains.score.adapter.output.persistence.repository.CommandScoreJpaRepository;
import hotil.baemo.domains.score.domain.aggregate.Score;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreBoardInternalAdapter {

    private final CommandScoreJpaRepository scoreRepository;

    public void addInitScore(Long matchId) {
        Score score = Score.initializeScore(new MatchId(matchId));
        scoreRepository.save(ScoreEntityMapper.toEntityWithId(score));
    }

    public void deleteScore(Long matchId) {
        scoreRepository.deleteByMatchId(matchId);
    }


    public void deleteScores(List<Long> matchIds) {
        scoreRepository.deleteAllById(matchIds);
    }
}
