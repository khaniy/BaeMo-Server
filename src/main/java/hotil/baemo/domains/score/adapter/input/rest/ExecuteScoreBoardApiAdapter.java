package hotil.baemo.domains.score.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.score.application.usecase.EndScoreBoardUseCase;
import hotil.baemo.domains.score.application.usecase.StartScoreBoardUseCase;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.user.UserId;
import hotil.baemo.domains.score.domain.value.user.UserName;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게임 점수판 관련 API")
@RequestMapping("/api/match")
@RestController
@RequiredArgsConstructor
public class ExecuteScoreBoardApiAdapter {

    private final StartScoreBoardUseCase startScoreBoardUseCase;
    private final EndScoreBoardUseCase endScoreBoardUseCase;

    @Operation(summary = "점수판 기능 시작 (심판 위임)")
    @PostMapping("/{matchId}/scoreboard")
    public ResponseDTO<Void> startScoreBoard(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long matchId
    ) {
        startScoreBoardUseCase.startScoreBoard(
            new MatchId(matchId),
            new UserId(user.userId()),
            new UserName(user.realName())
        );
        return ResponseDTO.ok();
    }

    @Operation(summary = "점수판 기능 중단 (심판 해임)")
    @DeleteMapping("/{matchId}/scoreboard")
    public ResponseDTO<Void> endScoreBoard(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long matchId
    ) {
        endScoreBoardUseCase.stopScoreBoard(
            new MatchId(matchId),
            new UserId(user.userId()));

        return ResponseDTO.ok();
    }
}