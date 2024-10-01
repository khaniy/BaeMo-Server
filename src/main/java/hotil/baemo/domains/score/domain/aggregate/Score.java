package hotil.baemo.domains.score.domain.aggregate;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.score.domain.value.score.ScoreId;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.score.*;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Score {
    private final ScoreId scoreId;
    private final MatchId matchId;
    private TeamPointLog teamAPointLog;
    private TeamPointLog teamBPointLog;
    private TeamPoint teamAPoint;
    private TeamPoint teamBPoint;
    private ScoreLog scoreLog;

    @Builder
    private Score(ScoreId scoreId, MatchId matchId, TeamPointLog teamAPointLog, TeamPointLog teamBPointLog, TeamPoint teamAPoint, TeamPoint teamBPoint, ScoreLog scoreLog) {
        this.scoreId = scoreId;
        this.matchId = matchId;
        this.teamAPointLog = teamAPointLog;
        this.teamBPointLog = teamBPointLog;
        this.teamAPoint = teamAPoint;
        this.teamBPoint = teamBPoint;
        this.scoreLog = scoreLog;
    }

    public static Score initializeScore(MatchId matchId) {
        return Score.builder()
                .matchId(matchId)
                .teamAPointLog(TeamPointLog.init())
                .teamBPointLog(TeamPointLog.init())
                .teamAPoint(TeamPoint.init())
                .teamBPoint(TeamPoint.init())
                .scoreLog(ScoreLog.init())
                .build();
    }

    public void scoreTeamA(){
        teamAPoint = teamAPoint.plus();
        teamAPointLog.add(teamAPoint);
        teamBPointLog.add(teamBPoint);
        scoreLog.add(Team.TEAM_A);

    }
    public void scoreTeamB(){
        teamBPoint = teamBPoint.plus();
        teamAPointLog.add(teamAPoint);
        teamBPointLog.add(teamBPoint);
        scoreLog.add(Team.TEAM_B);
    }

    public void revertScore(){
        switch (scoreLog.getLastScoredTeam()) {
            case TEAM_A ->{
                teamAPoint = teamAPoint.minus();
                teamAPointLog.pop();
                teamBPointLog.pop();
                scoreLog.pop();
            }
            case TEAM_B -> {
                teamBPoint = teamBPoint.minus();
                teamAPointLog.pop();
                teamBPointLog.pop();
                scoreLog.pop();
            }
            default -> throw new CustomException(ResponseCode.ETC_ERROR);

        }
    }

    public void update(TeamPointLog teamAPointLog, TeamPointLog teamBPointLog, TeamPoint teamAPoint, TeamPoint teamBPoint, ScoreLog scoreLog) {
        this.teamAPointLog = teamAPointLog;
        this.teamBPointLog = teamBPointLog;
        this.teamAPoint = teamAPoint;
        this.teamBPoint = teamBPoint;
        this.scoreLog = scoreLog;
    }

    public void updateTeamPoints(TeamPoint teamAPoint, TeamPoint teamBPoint) {
        this.teamAPoint = teamAPoint;
        this.teamBPoint = teamBPoint;
        this.teamAPointLog = TeamPointLog.init();
        this.teamBPointLog = TeamPointLog.init();
        this.scoreLog = ScoreLog.init();
    }
}

