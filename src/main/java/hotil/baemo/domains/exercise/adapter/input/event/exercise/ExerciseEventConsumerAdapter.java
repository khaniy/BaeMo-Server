package hotil.baemo.domains.exercise.adapter.input.event.exercise;


import hotil.baemo.core.event.ClubTopic;
import hotil.baemo.core.event.UserTopic;
import hotil.baemo.domains.exercise.application.usecases.exercise.command.DeleteExerciseUseCase;
import hotil.baemo.domains.exercise.application.usecases.user.command.ExpelExerciseUserUseCase;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseEventConsumerAdapter {

    private final DeleteExerciseUseCase deleteExerciseUseCase;
    private final ExpelExerciseUserUseCase expelExerciseUserUseCase;

    @Async
    @EventListener
    public void userDeleted(UserTopic.DeletedEvent event) {
        expelExerciseUserUseCase.expelAllActiveExercises(new UserId(event.userId()));
    }

    @Async
    @EventListener
    public void clubDeleted(ClubTopic.DeletedEvent event) {
        deleteExerciseUseCase.deleteAllActiveClubExercises(new ClubId(event.clubsId()));
    }

    @Async
    @EventListener
    public void clubUserCanceled(ClubTopic.UserExpelledEvent event) {
        expelExerciseUserUseCase.expelAllActiveClubExercises(new ClubId(event.clubsId()), new UserId(event.userId()));
    }

    @Async
    @EventListener
    public void clubUserCanceled(ClubTopic.UserLeftEvent event) {
        expelExerciseUserUseCase.expelAllActiveClubExercises(new ClubId(event.clubsId()), new UserId(event.userId()));
    }
}
