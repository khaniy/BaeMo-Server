package hotil.baemo.domains.exercise.adapter.input.rest.score;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.exercise.adapter.input.rest.score.dto.ScoreRequestDTO;
import hotil.baemo.domains.exercise.adapter.input.rest.score.mapper.ScoreRequestMapper;
import hotil.baemo.domains.exercise.application.usecases.score.UpdateScoreBoardUseCase;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
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
public class UpdateScoreBoardApiAdapter {

    private final UpdateScoreBoardUseCase updateScoreBoardUseCase;

    @Operation(summary = "점수판 점수 수정")
    @PutMapping("/{matchId}/scoreboard")
    public ResponseDTO<Void> updateMatchScore(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long matchId,
        @RequestBody ScoreRequestDTO.UpdateScoreBoard request
    ) {
        switch (request.action()) {
            case SCORE_TEAM_A -> updateScoreBoardUseCase.scoreTeamA(new MatchId(matchId), new UserId(user.userId()));
            case SCORE_TEAM_B -> updateScoreBoardUseCase.scoreTeamB(new MatchId(matchId), new UserId(user.userId()));
            case REVERT_SCORE -> updateScoreBoardUseCase.revertScore(new MatchId(matchId), new UserId(user.userId()));
        }
        return ResponseDTO.ok();
    }

    @Operation(summary = "점수판 팀 지정")
    @PutMapping("/{matchId}/scoreboard/member")
    public ResponseDTO<Void> updateMatchTeam(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long matchId,
        @RequestBody ScoreRequestDTO.UpdateMatchUser request
    ) {
        MatchId matchId_ = new MatchId(matchId);
        updateScoreBoardUseCase.updateMatchTeam(
            matchId_,
            new UserId(user.userId()),
            ScoreRequestMapper.toMatchUsers(matchId_, request)
        );
        return ResponseDTO.ok();
    }

}