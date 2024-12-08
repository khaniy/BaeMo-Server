package hotil.baemo.domains.exercise.application.usecases.score;

import hotil.baemo.domains.exercise.adapter.input.event.dto.EventScoreBoardDTO;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ConnectScoreBoardUseCase {

    SseEmitter subscribeScoreBoard(UserId userId, MatchId matchId);


    EventScoreBoardDTO.Init tempScoreBoard(UserId userId, MatchId matchId);
}
