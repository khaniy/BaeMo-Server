package hotil.baemo.domains.exercise.adapter.input.rest.match;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.exercise.adapter.input.rest.match.dto.request.MatchRequest;
import hotil.baemo.domains.exercise.application.usecases.match.command.CreateMatchUseCase;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게임 관련 API")
@RequestMapping("/api/match")
@RestController
@RequiredArgsConstructor
public class CreateMatchApiAdapter {

    private final CreateMatchUseCase createMatchUseCase;

    @Operation(summary = "게임 생성")
    @PostMapping("")
    public ResponseDTO<Void> createMatch(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @Valid @RequestBody MatchRequest.CreateMatchDTO request
    ) {
        createMatchUseCase.createMatch(
            new UserId(user.userId()),
            new ExerciseId(request.exerciseId()),
            request.toMatchUsers()
        );
        return ResponseDTO.ok();
    }
}
