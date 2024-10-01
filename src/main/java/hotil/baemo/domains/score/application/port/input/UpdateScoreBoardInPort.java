package hotil.baemo.domains.score.application.port.input;

import hotil.baemo.domains.score.application.port.output.CommandScoreBoardOutPort;
import hotil.baemo.domains.score.application.port.output.MatchExternalOutPort;
import hotil.baemo.domains.score.application.port.output.ScoreBoardEventOutPort;
import hotil.baemo.domains.score.application.usecase.UpdateScoreBoardUseCase;
import hotil.baemo.domains.score.domain.aggregate.MatchUser;
import hotil.baemo.domains.score.domain.aggregate.ScoreBoard;
import hotil.baemo.domains.score.domain.spec.ScoreBoardRoleSpecification;
import hotil.baemo.domains.score.domain.spec.UpdateScoreBoardSpecification;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.score.ScoreBoardRole;
import hotil.baemo.domains.score.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateScoreBoardInPort implements UpdateScoreBoardUseCase {

    private final ScoreBoardRoleSpecification scoreBoardRoleSpecification;
    private final CommandScoreBoardOutPort commandScoreBoardOutPort;
    private final ScoreBoardEventOutPort scoreBoardEventOutPort;
    private final MatchExternalOutPort matchExternalOutPort;

    @Override
    public void scoreTeamA(MatchId matchId, UserId userId) {
        ScoreBoard scoreBoard = commandScoreBoardOutPort.getScoreBoard(matchId);
        ScoreBoardRole role = scoreBoardRoleSpecification.getRole(matchId, userId);

        UpdateScoreBoardSpecification.spec(role).scoreTeamA(scoreBoard);

        commandScoreBoardOutPort.save(scoreBoard);
        scoreBoardEventOutPort.scoreBoardUpdated(scoreBoard);
    }

    @Override
    public void scoreTeamB(MatchId matchId, UserId userId) {
        ScoreBoard scoreBoard = commandScoreBoardOutPort.getScoreBoard(matchId);
        ScoreBoardRole role = scoreBoardRoleSpecification.getRole(matchId, userId);

        UpdateScoreBoardSpecification.spec(role).scoreTeamB(scoreBoard);

        commandScoreBoardOutPort.save(scoreBoard);
        scoreBoardEventOutPort.scoreBoardUpdated(scoreBoard);
    }

    @Override
    public void revertScore(MatchId matchId, UserId userId) {
        ScoreBoard scoreBoard = commandScoreBoardOutPort.getScoreBoard(matchId);
        ScoreBoardRole role = scoreBoardRoleSpecification.getRole(matchId, userId);

        UpdateScoreBoardSpecification.spec(role).revertScore(scoreBoard);

        commandScoreBoardOutPort.save(scoreBoard);
        scoreBoardEventOutPort.scoreBoardUpdated(scoreBoard);
    }

    @Override
    public void updateMatchTeam(MatchId matchId, UserId userId, List<MatchUser> matchUsers) {
        ScoreBoardRole role = scoreBoardRoleSpecification.getRole(matchId, userId);

        UpdateScoreBoardSpecification.spec(role);

        matchExternalOutPort.saveMatchUsers(matchId, matchUsers);
    }
}
