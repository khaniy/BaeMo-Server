package hotil.baemo.domains.exercise.adapter.input.rest.score;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.exercise.adapter.input.event.dto.EventScoreBoardDTO;
import hotil.baemo.domains.exercise.application.usecases.score.ConnectScoreBoardUseCase;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Tag(name = "게임 점수판 관련 API")
@RequestMapping("/api/match")
@RestController
@RequiredArgsConstructor
public class ConnectScoreBoardApiAdapter {

    private final ConnectScoreBoardUseCase connectScoreBoardUseCase;

    @Operation(summary = "점수판 구독")
    @GetMapping(value = "/connect/{matchId}/scoreboard", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeScoreBoard(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long matchId,
        HttpServletResponse response
    ) {
        System.out.println("user = " + user);

        // X-Accel-Buffering 헤더 추가하여 NGINX 버퍼링 비활성화
        response.setHeader("X-Accel-Buffering", "no");

        return connectScoreBoardUseCase.subscribeScoreBoard(
            new UserId(user.userId()),
            new MatchId(matchId)
        );
    }

    @Operation(summary = "점수판 구독(임시)")
    @GetMapping(value = "temp/{matchId}/scoreboard")
    public ResponseDTO<EventScoreBoardDTO.Init> subscribeScoreBoardTemp(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long matchId
    ) {
        return ResponseDTO.ok(connectScoreBoardUseCase.tempScoreBoard(
            new UserId(user.userId()),
            new MatchId(matchId))
        );
    }
}