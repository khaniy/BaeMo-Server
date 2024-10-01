package hotil.baemo.domains.score.application.port.input;

import hotil.baemo.config.cache.CacheConfig;
import hotil.baemo.config.cache.CacheProperties;
import hotil.baemo.config.cache.CacheProperties.CacheValue;
import hotil.baemo.domains.score.application.port.output.CommandScoreBoardOutPort;
import hotil.baemo.domains.score.application.port.output.CommandScoreOutPort;
import hotil.baemo.domains.score.application.port.output.MatchExternalOutPort;
import hotil.baemo.domains.score.application.port.output.ScoreBoardEventOutPort;
import hotil.baemo.domains.score.application.usecase.EndScoreBoardUseCase;
import hotil.baemo.domains.score.application.usecase.StartScoreBoardUseCase;
import hotil.baemo.domains.score.domain.spec.ScoreBoardRoleSpecification;
import hotil.baemo.domains.score.domain.spec.StartScoreBoardSpecification;
import hotil.baemo.domains.score.domain.value.match.MatchStatus;
import hotil.baemo.domains.score.domain.value.score.ScoreBoardRole;
import hotil.baemo.domains.score.domain.aggregate.Match;
import hotil.baemo.domains.score.domain.aggregate.Score;
import hotil.baemo.domains.score.domain.aggregate.ScoreBoard;
import hotil.baemo.domains.score.domain.spec.StopScoreBoardSpecification;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.user.UserId;
import hotil.baemo.domains.score.domain.value.user.UserName;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ExecuteScoreBoardInPort implements StartScoreBoardUseCase, EndScoreBoardUseCase {

    private final ScoreBoardRoleSpecification scoreBoardRoleSpecification;
    private final CommandScoreBoardOutPort commandScoreBoardOutPort;
    private final CommandScoreOutPort commandScoreOutPort;
    private final ScoreBoardEventOutPort scoreBoardEventOutPort;
    private final MatchExternalOutPort matchExternalOutPort;


    @Override
    public void startScoreBoard(MatchId matchId, UserId userId, UserName userName) {
        Score score = commandScoreOutPort.getScore(matchId);
        Match match = matchExternalOutPort.getMatch(matchId);
        ScoreBoardRole role = scoreBoardRoleSpecification.getRole(matchId, userId);
        ScoreBoard scoreBoard = StartScoreBoardSpecification.spec(role, match)
            .startScoreBoard(userId, userName, score);

        matchExternalOutPort.updateMatchStatus(matchId, MatchStatus.PROGRESS_SCORING);
        commandScoreBoardOutPort.save(scoreBoard);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = CacheValue.SCOREBOARD_CACHE, key = "'match_users_'+#matchId.id()"),
        @CacheEvict(value = CacheValue.SCOREBOARD_CACHE, key = "'match_detail_'+#matchId.id()")
    })
    public void stopScoreBoard(MatchId matchId, UserId userId) {
        ScoreBoard scoreBoard = commandScoreBoardOutPort.getScoreBoard(matchId);
        Score score = commandScoreOutPort.getScore(matchId);
        ScoreBoardRole role = scoreBoardRoleSpecification.getRole(matchId, userId);
        Score updatedScore = StopScoreBoardSpecification.spec(role)
            .endScoreBoard(scoreBoard, score);

        commandScoreOutPort.saveScore(updatedScore);
        matchExternalOutPort.updateMatchStatus(matchId, MatchStatus.PROGRESS);
        commandScoreBoardOutPort.deleteScoreBoard(scoreBoard);
        scoreBoardEventOutPort.scoreBoardStopped(scoreBoard);
    }
}
