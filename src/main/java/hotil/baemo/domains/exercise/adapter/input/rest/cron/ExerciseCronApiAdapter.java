package hotil.baemo.domains.exercise.adapter.input.rest.cron;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.domains.exercise.application.usecases.exercise.command.UpdateExerciseStatusUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "쿠버네티스 내부 cron job을 위한 API")
@RequestMapping("/cron/exercises")
@RestController
@RequiredArgsConstructor
public class ExerciseCronApiAdapter {
    // Todo User Id 처리

    private final UpdateExerciseStatusUseCase updateExerciseStatusUseCase;

    @Operation(summary = "운동 시작 상태 수정(매 분마다)")
    @PutMapping("/start")
    public ResponseDTO<Void> updateStartExerciseStatus() {
        updateExerciseStatusUseCase.progressExercisesFromNow();
        return ResponseDTO.ok();
    }

    @Operation(summary = "운동 완료 상태 수정(정각, 밤 24시)")
    @PutMapping("/end")
    public ResponseDTO<Void> updateEndExerciseStatus() {
        updateExerciseStatusUseCase.completeExercisesFromNow();
        return ResponseDTO.ok();
    }
}
