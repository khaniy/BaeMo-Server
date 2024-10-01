package hotil.baemo.domains.score.application.port.input;

import hotil.baemo.domains.score.application.port.output.CommandScoreOutPort;
import hotil.baemo.domains.score.application.usecase.UpdateScoreUseCase;
import hotil.baemo.domains.score.domain.aggregate.Score;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.score.TeamPoint;
import hotil.baemo.domains.score.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateScoreInPort implements UpdateScoreUseCase {

    private final CommandScoreOutPort commandScoreOutPort;


    @Override
    public void updateScorePoint(MatchId matchId, UserId userId, TeamPoint teamAPoint, TeamPoint teamBPoint) {
        Score score = commandScoreOutPort.getScore(matchId);
        score.updateTeamPoints(teamAPoint, teamBPoint);
        commandScoreOutPort.saveScore(score);
    }
}
