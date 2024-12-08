package hotil.baemo.domains.exercise.adapter.input.rest.match;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.exercise.application.usecases.match.command.DeleteMatchUseCase;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게임 관련 API")
@RequestMapping("/api/match")
@RestController
@RequiredArgsConstructor
public class DeleteMatchApiAdapter {

    private final DeleteMatchUseCase deleteMatchUseCase;

    @Operation(summary = "게임 삭제")
    @DeleteMapping("/{matchId}")
    public ResponseDTO<Void> deleteMatch(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long matchId
    ) {
        deleteMatchUseCase.deleteMatch(
            new UserId(user.userId()),
            new MatchId(matchId)
        );
        return ResponseDTO.ok();
    }
}
