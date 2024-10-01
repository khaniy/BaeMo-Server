package hotil.baemo.domains.match.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.match.application.dto.QMatchDTO;
import hotil.baemo.domains.match.application.port.input.query.RetrieveMatchDetailInPort;
import hotil.baemo.domains.match.application.usecase.query.RetrieveAllMatchesUseCase;
import hotil.baemo.domains.match.application.usecase.query.RetrieveMatchDetailUseCase;
import hotil.baemo.domains.match.application.usecase.query.RetrieveProgressiveMatchesUseCase;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.user.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "게임 관련 API")
@RequestMapping("/api/match")
@RestController
@RequiredArgsConstructor
public class QueryMatchApiAdapter {
    private final RetrieveAllMatchesUseCase retrieveAllMatchesUseCase;
    private final RetrieveProgressiveMatchesUseCase retrieveProgressiveMatchesUseCase;
    private final RetrieveMatchDetailUseCase retrieveMatchDetailUseCase;


    @Operation(summary = "운동 내부 모든 게임 조회")
    @GetMapping("/exercise/{exerciseId}/all")
    public ResponseDTO<List<QMatchDTO.MatchList>> retrieveAllMatchByExercise(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long exerciseId
    ) {
        return ResponseDTO.ok(retrieveAllMatchesUseCase.retrieveAllMatches(
                new UserId(user.userId()),
                new ExerciseId(exerciseId)));
    }

    @Operation(summary = "운동 내부 진행 중인 게임 조회")
    @GetMapping("/exercise/{exerciseId}/progress")
    public ResponseDTO<List<QMatchDTO.MatchList>> retrieveProgressiveMatchByExercise(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long exerciseId
    ) {
        return ResponseDTO.ok(retrieveProgressiveMatchesUseCase.retrieveProgressMatches(
                new UserId(user.userId()),
                new ExerciseId(exerciseId)));
    }

    @Operation(summary = "게임 상세 조회")
    @GetMapping("/{matchId}")
    public ResponseDTO<QMatchDTO.MatchDetail> retrieveMatchDetail(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long matchId
    ) {
        return ResponseDTO.ok(retrieveMatchDetailUseCase.retrieveMatchDetail(
            new UserId(user.userId()),
            new MatchId(matchId)));
    }
}
