package hotil.baemo.domains.exercise.adapter.input.event.dto;

import hotil.baemo.core.event.ScoreBoardTopic;
import hotil.baemo.domains.exercise.domain.entity.score.ScoreBoard;

public class ScoreBoardEventMapper {

    public static ScoreBoardTopic.ScoreUpdatedEvent toUpdated(ScoreBoard scoreBoard) {
        return new ScoreBoardTopic.ScoreUpdatedEvent(
            scoreBoard.getScore().getMatchId().id(),
            scoreBoard.getScore().getScoreLog().scoreLog(),
            scoreBoard.getScore().getTeamAPointLog().getPointLog(),
            scoreBoard.getScore().getTeamBPointLog().getPointLog(),
            scoreBoard.getScore().getTeamAPoint().teamPoint(),
            scoreBoard.getScore().getTeamBPoint().teamPoint()
        );
    }

    public static ScoreBoardTopic.ScoreStoppedEvent toStopped(ScoreBoard scoreBoard) {
        return new ScoreBoardTopic.ScoreStoppedEvent(
            scoreBoard.getScore().getMatchId().id(),
            scoreBoard.getScore().getScoreLog().scoreLog(),
            scoreBoard.getScore().getTeamAPointLog().getPointLog(),
            scoreBoard.getScore().getTeamBPointLog().getPointLog(),
            scoreBoard.getScore().getTeamAPoint().teamPoint(),
            scoreBoard.getScore().getTeamBPoint().teamPoint()
        );
    }
}
