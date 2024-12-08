package hotil.baemo.domains.exercise.application.ports.input.score;

import hotil.baemo.domains.exercise.application.ports.output.score.CommandScoreBoardOutPort;
import hotil.baemo.domains.exercise.application.ports.output.score.ScoreBoardSSEOutPort;
import hotil.baemo.domains.exercise.application.usecases.score.ConnectScoreBoardUseCase;
import hotil.baemo.domains.exercise.domain.entity.score.ScoreBoard;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.adapter.input.event.dto.EventScoreBoardDTO;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class ConnectScoreBoardInPort implements ConnectScoreBoardUseCase {

    //    private final ScoreBoardRoleSpecification scoreBoardRoleSpecification;
    private final CommandScoreBoardOutPort commandScoreBoardOutPort;
//    private final ScoreExternalQuery scoreExternalQuery;
    private final ScoreBoardSSEOutPort sseScoreBoardOutPort;

    @Override
    @Transactional
    public SseEmitter subscribeScoreBoard(UserId userId, MatchId matchId) {
        ScoreBoard scoreBoard = commandScoreBoardOutPort.getScoreBoard(matchId);
//        ScoreBoardRole role = scoreBoardRoleSpecification.getRole(matchId, userId);
//        ConnectScoreBoardSpecification.spec(role)
//            .subscribeScoreBoard(userId, scoreBoard);
//        commandScoreBoardOutPort.save(scoreBoard);
        return sseScoreBoardOutPort.connect(userId, scoreBoard);
    }

    @Override
    @Transactional
    public EventScoreBoardDTO.Init tempScoreBoard(UserId userId, MatchId matchId) { //Todo SSE 전 임시용
        ScoreBoard scoreBoard = commandScoreBoardOutPort.getScoreBoard(matchId);
//        ScoreBoardRole role = scoreBoardRoleSpecification.getRole(matchId, userId);
//        ConnectScoreBoardSpecification.spec(role);
//        return scoreExternalQuery.findScoreInit(matchId.id(), scoreBoard);
        return EventScoreBoardDTO.Init.builder().build();
    }
}
