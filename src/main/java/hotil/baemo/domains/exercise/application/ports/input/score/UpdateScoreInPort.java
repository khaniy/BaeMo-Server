package hotil.baemo.domains.exercise.application.ports.input.score;

import hotil.baemo.domains.exercise.application.ports.output.score.CommandScoreOutPort;
import hotil.baemo.domains.exercise.application.usecases.score.UpdateScoreUseCase;
import hotil.baemo.domains.exercise.domain.entity.score.Score;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.score.TeamPoint;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
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
