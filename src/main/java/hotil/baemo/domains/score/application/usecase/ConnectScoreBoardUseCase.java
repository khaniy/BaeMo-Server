package hotil.baemo.domains.score.application.usecase;

import hotil.baemo.domains.score.adapter.event.dto.EventScoreBoardDTO;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.user.UserId;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ConnectScoreBoardUseCase {

    SseEmitter subscribeScoreBoard(UserId userId, MatchId matchId);


    EventScoreBoardDTO.Init tempScoreBoard(UserId userId, MatchId matchId);
}
