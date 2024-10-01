package hotil.baemo.domains.score.adapter.output.persistence;

import hotil.baemo.domains.score.adapter.output.persistence.entity.ScoreEntity;
import hotil.baemo.domains.score.adapter.output.persistence.mapper.ScoreEntityMapper;
import hotil.baemo.domains.score.adapter.output.persistence.repository.CommandScoreJpaRepository;
import hotil.baemo.domains.score.application.port.output.CommandScoreOutPort;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.aggregate.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommandScorePostgresAdapter implements CommandScoreOutPort {

    private final CommandScoreJpaRepository scoreRepository;

    @Override
    public void addInitScore(Score score) {
        scoreRepository.save(ScoreEntityMapper.toEntityWithId(score));
    }

    @Override
    public void saveScore(Score score) {
        scoreRepository.save(ScoreEntityMapper.toEntityWithId(score));
    }

    @Override
    public Score getScore(MatchId matchId) {
        ScoreEntity scoreEntity = scoreRepository.findByMatchId(matchId.id());
        return ScoreEntityMapper.toScore(scoreEntity);
    }

    @Override
    public void deleteScore(MatchId matchId) {
        scoreRepository.deleteByMatchId(matchId.id());
    }
}
