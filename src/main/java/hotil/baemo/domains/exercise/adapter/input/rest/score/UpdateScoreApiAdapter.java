package hotil.baemo.domains.exercise.adapter.input.rest.score;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.exercise.adapter.input.rest.score.dto.ScoreRequestDTO;
import hotil.baemo.domains.exercise.application.usecases.score.UpdateScoreUseCase;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.score.TeamPoint;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게임 점수판 관련 API")
@RequestMapping("/api/match")
@RestController
@RequiredArgsConstructor
public class UpdateScoreApiAdapter {

    private final UpdateScoreUseCase updateScoreUseCase;

    @Operation(summary = "게임 점수 수정")
    @PutMapping("/{matchId}/score")
    public ResponseDTO<Void> updateMatchScore(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long matchId,
        @RequestBody ScoreRequestDTO.UpdateScore request
    ) {
        updateScoreUseCase.updateScorePoint(
            new MatchId(matchId),
            new UserId(user.userId()),
            new TeamPoint(request.teamAPoint()),
            new TeamPoint(request.teamBPoint())
        );
        return ResponseDTO.ok();
    }

}