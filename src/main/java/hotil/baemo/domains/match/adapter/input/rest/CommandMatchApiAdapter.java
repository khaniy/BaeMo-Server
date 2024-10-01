package hotil.baemo.domains.match.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.match.adapter.input.rest.dto.request.MatchRequest;
import hotil.baemo.domains.match.adapter.input.rest.mapper.MatchUserDTOMapper;
import hotil.baemo.domains.match.application.usecase.command.CreateMatchUseCase;
import hotil.baemo.domains.match.application.usecase.command.DeleteMatchUseCase;
import hotil.baemo.domains.match.application.usecase.command.UpdateMatchUseCase;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.match.domain.value.match.CourtNumber;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.user.UserId;
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
public class CommandMatchApiAdapter {
    private final CreateMatchUseCase createMatchUseCase;
    private final UpdateMatchUseCase updateMatchUseCase;
    private final DeleteMatchUseCase deleteMatchUseCase;

    @Operation(summary = "게임 생성")
    @PostMapping("")
    public ResponseDTO<Void> createMatch(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @Valid @RequestBody MatchRequest.CreateMatchDTO request
    ) {
        createMatchUseCase.createMatch(
            new UserId(user.userId()),
            new ExerciseId(request.exerciseId()),
            MatchUserDTOMapper.toDomain(request.matchUsers()),
            new CourtNumber(request.courtNumber())
        );
        return ResponseDTO.ok();
    }

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
            new CourtNumber(request.courtNumber()),
            MatchUserDTOMapper.toDomain(request.matchUsers())
        );
        return ResponseDTO.ok();
    }

    @Operation(summary = "게임 상태 변경")
    @PutMapping("/{matchId}/status")
    public ResponseDTO<Void> updateNextMatchStatus(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @RequestBody MatchRequest.UpdateMatchStatusDTO request,
        @PathVariable Long matchId
    ) {
        switch (request.action()) {
            case NEXT -> updateMatchUseCase.updateNextStatus(
                new UserId(user.userId()),
                new MatchId(matchId));
            case PREVIOUS -> updateMatchUseCase.updatePreviousStatus(
                new UserId(user.userId()),
                new MatchId(matchId));
        }
        return ResponseDTO.ok();
    }

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


//    @Operation(summary = "게임 수정(게임 순서)", description = "최소 2개 이상 (순서를 교환하는 개념)")
//    @PutMapping("/orders")
//    public ResponseDTO<Void> updateMatchOrder(
//        @AuthenticationPrincipal BaeMoUserDetails user,
//        @Valid @RequestBody MatchRequest.UpdateMatchOrderDTO request
//    ) {
//        updateMatchUseCase.updateMatchOrders(
//            new UserId(user.userId()),
//            new ExerciseId(request.exerciseId()),
//            MatchOrderDTOMapper.toMatchOrders(request)
//        );
//        return ResponseDTO.ok();
//    }
}
