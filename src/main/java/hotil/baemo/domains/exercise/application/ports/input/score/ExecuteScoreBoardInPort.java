package hotil.baemo.domains.exercise.application.ports.input.score;

import hotil.baemo.config.cache.CacheProperties.CacheValue;
import hotil.baemo.domains.exercise.application.ports.output.score.CommandScoreBoardOutPort;
import hotil.baemo.domains.exercise.application.ports.output.score.CommandScoreOutPort;
import hotil.baemo.domains.exercise.application.ports.output.score.ScoreBoardEventOutPort;
import hotil.baemo.domains.exercise.application.usecases.score.EndScoreBoardUseCase;
import hotil.baemo.domains.exercise.application.usecases.score.StartScoreBoardUseCase;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import hotil.baemo.domains.exercise.domain.value.user.UserName;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ExecuteScoreBoardInPort implements StartScoreBoardUseCase, EndScoreBoardUseCase {

    //    private final ScoreBoardRoleSpecification scoreBoardRoleSpecification;
    private final CommandScoreBoardOutPort commandScoreBoardOutPort;
    private final CommandScoreOutPort commandScoreOutPort;
    private final ScoreBoardEventOutPort scoreBoardEventOutPort;


    @Override
    public void startScoreBoard(MatchId matchId, UserId userId, UserName userName) {
//        Score score = commandScoreOutPort.getScore(matchId);
//        Match match = matchExternalOutPort.getMatch(matchId);
//        ScoreBoardRole role = scoreBoardRoleSpecification.getRole(matchId, userId);
//        ScoreBoard scoreBoard = StartScoreBoardSpecification.spec(role, match)
//            .startScoreBoard(userId, userName, score);
//
//        matchExternalOutPort.updateMatchStatus(matchId, MatchStatus.PROGRESS_SCORING);
//        commandScoreBoardOutPort.save(scoreBoard);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = CacheValue.SCOREBOARD_CACHE, key = "'match_users_'+#matchId.id()"),
        @CacheEvict(value = CacheValue.SCOREBOARD_CACHE, key = "'match_detail_'+#matchId.id()")
    })
    public void stopScoreBoard(MatchId matchId, UserId userId) {
//        ScoreBoard scoreBoard = commandScoreBoardOutPort.getScoreBoard(matchId);
//        Score score = commandScoreOutPort.getScore(matchId);
//        ScoreBoardRole role = scoreBoardRoleSpecification.getRole(matchId, userId);
//        Score updatedScore = StopScoreBoardSpecification.spec(role)
//            .endScoreBoard(scoreBoard, score);
//
//        commandScoreOutPort.saveScore(updatedScore);
//        matchExternalOutPort.updateMatchStatus(matchId, MatchStatus.PROGRESS);
//        commandScoreBoardOutPort.deleteScoreBoard(scoreBoard);
//        scoreBoardEventOutPort.scoreBoardStopped(scoreBoard);
    }
}
