package hotil.baemo.domains.exercise.application.ports.input.score;

import hotil.baemo.domains.exercise.application.ports.output.score.CommandScoreBoardOutPort;
import hotil.baemo.domains.exercise.application.ports.output.score.ScoreBoardEventOutPort;
import hotil.baemo.domains.exercise.application.usecases.score.UpdateScoreBoardUseCase;
import hotil.baemo.domains.exercise.domain.entity.score.ScoreBoard;
import hotil.baemo.domains.exercise.domain.entity.user.MatchUser;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateScoreBoardInPort implements UpdateScoreBoardUseCase {

    //    private final ScoreBoardRoleSpecification scoreBoardRoleSpecification;
    private final CommandScoreBoardOutPort commandScoreBoardOutPort;
    private final ScoreBoardEventOutPort scoreBoardEventOutPort;
//    private final MatchExternalOutPort matchExternalOutPort;

    @Override
    public void scoreTeamA(MatchId matchId, UserId userId) {
        ScoreBoard scoreBoard = commandScoreBoardOutPort.getScoreBoard(matchId);
//        ScoreBoardRole role = scoreBoardRoleSpecification.getRole(matchId, userId);
//
//        UpdateScoreBoardSpecification.spec(role).scoreTeamA(scoreBoard);

        commandScoreBoardOutPort.save(scoreBoard);
        scoreBoardEventOutPort.scoreBoardUpdated(scoreBoard);
    }

    @Override
    public void scoreTeamB(MatchId matchId, UserId userId) {
        ScoreBoard scoreBoard = commandScoreBoardOutPort.getScoreBoard(matchId);
//        ScoreBoardRole role = scoreBoardRoleSpecification.getRole(matchId, userId);
//
//        UpdateScoreBoardSpecification.spec(role).scoreTeamB(scoreBoard);

        commandScoreBoardOutPort.save(scoreBoard);
        scoreBoardEventOutPort.scoreBoardUpdated(scoreBoard);
    }

    @Override
    public void revertScore(MatchId matchId, UserId userId) {
        ScoreBoard scoreBoard = commandScoreBoardOutPort.getScoreBoard(matchId);
//        ScoreBoardRole role = scoreBoardRoleSpecification.getRole(matchId, userId);
//
//        UpdateScoreBoardSpecification.spec(role).revertScore(scoreBoard);

        commandScoreBoardOutPort.save(scoreBoard);
        scoreBoardEventOutPort.scoreBoardUpdated(scoreBoard);
    }

    @Override
    public void updateMatchTeam(MatchId matchId, UserId userId, List<MatchUser> matchUsers) {
//        ScoreBoardRole role = scoreBoardRoleSpecification.getRole(matchId, userId);
//
//        UpdateScoreBoardSpecification.spec(role);
//
//        matchExternalOutPort.saveMatchUsers(matchId, matchUsers);
    }
}
