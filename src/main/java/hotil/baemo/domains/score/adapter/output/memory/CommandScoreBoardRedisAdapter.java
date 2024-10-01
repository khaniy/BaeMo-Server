package hotil.baemo.domains.score.adapter.output.memory;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.score.adapter.output.memory.mapper.ScoreBoardRedisMapper;
import hotil.baemo.domains.score.adapter.output.memory.repository.ScoreBoardRedisRepository;
import hotil.baemo.domains.score.application.port.output.CommandScoreBoardOutPort;
import hotil.baemo.domains.score.domain.aggregate.ScoreBoard;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandScoreBoardRedisAdapter implements CommandScoreBoardOutPort {

    private final ScoreBoardRedisRepository scoreBoardRepository;

    @Override
    public void save(ScoreBoard scoreBoard) {
        scoreBoardRepository.save(
            ScoreBoardRedisMapper.toRedisKey(scoreBoard.getMatchId()),
            ScoreBoardRedisMapper.toEntity(scoreBoard)
        );
    }

    @Override
    public ScoreBoard getScoreBoard(MatchId matchId) {
        final var entity = scoreBoardRepository.find(ScoreBoardRedisMapper.toRedisKey(matchId))
            .orElseThrow(() -> new CustomException(ResponseCode.MATCH_NOT_FOUND));
        return ScoreBoardRedisMapper.toScoreBoard(matchId.id(), entity);
    }

    @Override
    public ScoreBoard getScoreBoardOptional(MatchId matchId) {
        final var entity = scoreBoardRepository.find(ScoreBoardRedisMapper.toRedisKey(matchId));
        return entity.map(scoreBoardEntity -> ScoreBoardRedisMapper.toScoreBoard(matchId.id(), scoreBoardEntity))
            .orElse(null);

    }

    @Override
    public void deleteScoreBoard(ScoreBoard scoreBoard) {
        scoreBoardRepository.delete(ScoreBoardRedisMapper.toRedisKey(scoreBoard.getMatchId()));
    }

}

