package hotil.baemo.domains.score.application.port.input;

import hotil.baemo.domains.score.adapter.event.dto.EventScoreBoardDTO;
import hotil.baemo.domains.score.adapter.output.external.query.ScoreExternalQuery;
import hotil.baemo.domains.score.application.port.output.CommandScoreBoardOutPort;
import hotil.baemo.domains.score.application.port.output.ScoreBoardSSEOutPort;
import hotil.baemo.domains.score.application.usecase.ConnectScoreBoardUseCase;
import hotil.baemo.domains.score.domain.aggregate.ScoreBoard;
import hotil.baemo.domains.score.domain.spec.ConnectScoreBoardSpecification;
import hotil.baemo.domains.score.domain.spec.ScoreBoardRoleSpecification;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.score.ScoreBoardRole;
import hotil.baemo.domains.score.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class ConnectScoreBoardInPort implements ConnectScoreBoardUseCase {

    private final ScoreBoardRoleSpecification scoreBoardRoleSpecification;
    private final CommandScoreBoardOutPort commandScoreBoardOutPort;
    private final ScoreExternalQuery scoreExternalQuery;
    private final ScoreBoardSSEOutPort sseScoreBoardOutPort;

    @Override
    @Transactional
    public SseEmitter subscribeScoreBoard(UserId userId, MatchId matchId) {
        ScoreBoard scoreBoard = commandScoreBoardOutPort.getScoreBoard(matchId);
        ScoreBoardRole role = scoreBoardRoleSpecification.getRole(matchId, userId);
        ConnectScoreBoardSpecification.spec(role)
            .subscribeScoreBoard(userId, scoreBoard);
        commandScoreBoardOutPort.save(scoreBoard);
        return sseScoreBoardOutPort.connect(userId, scoreBoard);
    }

    @Override
    @Transactional
    public EventScoreBoardDTO.Init tempScoreBoard(UserId userId, MatchId matchId) { //Todo SSE 전 임시용
        ScoreBoard scoreBoard = commandScoreBoardOutPort.getScoreBoard(matchId);
        ScoreBoardRole role = scoreBoardRoleSpecification.getRole(matchId, userId);
        ConnectScoreBoardSpecification.spec(role);
        return scoreExternalQuery.findScoreInit(matchId.id(), scoreBoard);
    }
}
