package hotil.baemo.domains.exercise.adapter.input.rest.match;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.exercise.adapter.input.rest.match.dto.request.MatchRequest;
import hotil.baemo.domains.exercise.application.usecases.match.command.UpdateMatchUseCase;
import hotil.baemo.domains.exercise.domain.value.exercise.CourtNumber;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게임 관련 API")
@RequestMapping("/api/match")
@RestController
@RequiredArgsConstructor
public class UpdateMatchApiAdapter {

    private final UpdateMatchUseCase updateMatchUseCase;

    @Operation(summary = "게임 수정(메타 정보)")
    @PutMapping("/{matchId}")
    public ResponseDTO<Void> updateMatch(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long matchId,
        @Valid @RequestBody MatchRequest.UpdateMatchDTO request
    ) {
        updateMatchUseCase.updateMatch(
            new UserId(user.userId()),
            new MatchId(matchId),
            request.toMatchUsers()
        );
        return ResponseDTO.ok();
    }

    @Operation(summary = "게임 상태 변경(신 버전)")
    @PatchMapping("/{matchId}")
    public ResponseDTO<Void> updateNextMatchStatus(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @RequestBody @Valid MatchRequest.UpdateMatchStatusDTO request,
        @PathVariable Long matchId
    ) {
        updateMatchUseCase.updateMatchStatus(
            new UserId(user.userId()),
            new MatchId(matchId),
            request.matchStatus(),
            request.courtNumber() != null ? new CourtNumber(request.courtNumber()) : null
        );
        return ResponseDTO.ok();
    }
}
