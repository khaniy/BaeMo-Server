package hotil.baemo.domains.exercise.adapter.input.scheduler;

import hotil.baemo.domains.exercise.application.usecases.exercise.command.UpdateExerciseStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExerciseScheduler {

    private final UpdateExerciseStatusUseCase updateExerciseStatusUseCase;

    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
    public void completeExercise() {
        updateExerciseStatusUseCase.completeExercisesFromNow();
    }
}
