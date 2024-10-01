package hotil.baemo.domains.score.adapter.event.dto;

import hotil.baemo.domains.score.domain.aggregate.ScoreBoard;

public class ScoreBoardEventMapper {

    public static EventScoreBoardDTO.Updated toEventDTO(ScoreBoard scoreBoard) {
        return new EventScoreBoardDTO.Updated(
            scoreBoard.getScore().getMatchId().id(),
            scoreBoard.getScore().getScoreLog().scoreLog(),
            scoreBoard.getScore().getTeamAPointLog().getPointLog(),
            scoreBoard.getScore().getTeamBPointLog().getPointLog(),
            scoreBoard.getScore().getTeamAPoint().teamPoint(),
            scoreBoard.getScore().getTeamBPoint().teamPoint()
        );
    }
}
