package hotil.baemo.domains.exercise.adapter.input.rest.court;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.exercise.adapter.input.rest.court.dto.ExerciseCourtRequest;
import hotil.baemo.domains.exercise.application.usecases.court.command.CreateExerciseCourtUseCase;
import hotil.baemo.domains.exercise.application.usecases.court.command.DeleteExerciseCourtUseCase;
import hotil.baemo.domains.exercise.application.usecases.court.command.UpdateExerciseCourtUseCase;
import hotil.baemo.domains.exercise.domain.value.exercise.CourtNumber;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseCourtId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "운동 코트 생성 & 수정 & 삭제 관련 API")
@RequestMapping("/api/exercises")
@RestController
@RequiredArgsConstructor
public class DeleteExerciseCourtApiAdapter {

    private final DeleteExerciseCourtUseCase deleteExerciseCourtUseCase;

    @Operation(summary = "코트 삭제하기")
    @DeleteMapping("/{exerciseId}/court/{exerciseCourtId}")
    public ResponseDTO deleteCourt(
        @AuthenticationPrincipal BaeMoUserDetails user,
        @PathVariable @NotNull @Positive Long exerciseId,
        @PathVariable @NotNull @Positive Long exerciseCourtId
    ) {

        deleteExerciseCourtUseCase.deleteExerciseCourt(
            new UserId(user.userId()),
            new ExerciseId(exerciseId),
            new ExerciseCourtId(exerciseCourtId)
        );
        return ResponseDTO.ok();
    }
}
