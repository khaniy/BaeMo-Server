package hotil.baemo.domains.exercise.adapter.input.rest.court;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.exercise.application.dto.QExerciseCourtDTO;
import hotil.baemo.domains.exercise.application.usecases.court.query.RetrieveExerciseCourtUseCase;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "운동 코트 조회 관련 API")
@RequestMapping("/api/exercises")
@RestController
@RequiredArgsConstructor
public class QueryExerciseCourtApiAdapter {

    private final RetrieveExerciseCourtUseCase retrieveExerciseCourtUseCase;

    @Operation(summary = "코트 조회(코트만 조회용)")
    @GetMapping("/{exerciseId}/court")
    public ResponseDTO<List<QExerciseCourtDTO.ExerciseCourt>> retrieveParticipatedUser(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable Long exerciseId
    ) {

        return ResponseDTO.ok(retrieveExerciseCourtUseCase.retrieveExerciseCourt(new UserId(user.userId()), new ExerciseId(exerciseId)));
    }
}