package hotil.baemo.domains.exercise.adapter.input.event.match;


import hotil.baemo.core.event.ExerciseTopic;
import hotil.baemo.domains.exercise.application.ports.output.match.CommandMatchOutPort;
import hotil.baemo.domains.exercise.application.usecases.match.command.DeleteMatchUseCase;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class MatchEventConsumerAdapter {

    private final CommandMatchOutPort commandMatchOutPort;
    private final DeleteMatchUseCase deleteMatchUseCase;

//    @Async
//    @EventListener
//    public void exerciseCompleted(ExerciseTopic.CompletedEvent event) {
//        commandMatchOutPort.saveCompletedMatches(event.exerciseIds());
//    }
//
//    @Async
//    @EventListener
//    public void exerciseDeleted(ExerciseTopic.DeletedEvent event) {
//        deleteMatchUseCase.deleteMatchesByExercise(new ExerciseId(event.exerciseId()));
//    }

//    @Async
//    @EventListener
//    public void exerciseUserCancelled(ExerciseTopic.UserCancelledEvent event) {
//        deleteMatchUseCase.deleteMatchUsersByExerciseUser(new ExerciseId(event.exerciseId()), new UserId(event.userId()));
//    }
}
