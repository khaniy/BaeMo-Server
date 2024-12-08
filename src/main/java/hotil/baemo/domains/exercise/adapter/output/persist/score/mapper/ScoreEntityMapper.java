package hotil.baemo.domains.exercise.adapter.output.persist.score.mapper;

import hotil.baemo.domains.exercise.adapter.output.persist.score.entity.ScoreEntity;
import hotil.baemo.domains.exercise.domain.entity.score.Score;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.score.ScoreId;
import hotil.baemo.domains.exercise.domain.value.score.ScoreLog;
import hotil.baemo.domains.exercise.domain.value.score.TeamPoint;
import hotil.baemo.domains.exercise.domain.value.score.TeamPointLog;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScoreEntityMapper {

    public static ScoreEntity toEntityWithId(Score score) {
        return ScoreEntity.builder()
            .id(score.getScoreId() != null ? score.getScoreId().id() : null)
            .matchId(score.getMatchId().id())
            .teamAPointLog(score.getTeamAPointLog().getPointLog())
            .teamBPointLog(score.getTeamBPointLog().getPointLog())
            .teamAPoint(score.getTeamAPoint().teamPoint())
            .teamBPoint(score.getTeamBPoint().teamPoint())
            .scoreLog(score.getScoreLog().scoreLog())
            .build();
    }

    public static Score toScore(ScoreEntity scoreEntity) {
        return Score.builder()
            .scoreId(new ScoreId(scoreEntity.getId()))
            .matchId(new MatchId(scoreEntity.getMatchId()))
            .teamAPointLog(new TeamPointLog(scoreEntity.getTeamAPointLog().stream().map(TeamPoint::new).toList()))
            .teamBPointLog(new TeamPointLog(scoreEntity.getTeamBPointLog().stream().map(TeamPoint::new).toList()))
            .teamAPoint(new TeamPoint(scoreEntity.getTeamAPoint()))
            .teamBPoint(new TeamPoint(scoreEntity.getTeamBPoint()))
            .scoreLog(new ScoreLog(scoreEntity.getScoreLog()))
            .build();
    }

}