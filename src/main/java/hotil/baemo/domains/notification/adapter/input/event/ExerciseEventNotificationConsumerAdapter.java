package hotil.baemo.domains.notification.adapter.input.event;

import hotil.baemo.core.event.ExerciseTopic;
import hotil.baemo.domains.notification.application.usecase.NotifyExerciseUseCase;
import hotil.baemo.domains.notification.domains.value.club.ClubId;
import hotil.baemo.domains.notification.domains.value.exercise.*;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseEventNotificationConsumerAdapter {

    private final NotifyExerciseUseCase notifyExerciseUseCase;

    @Async
    @EventListener
    public void exerciseCreated(ExerciseTopic.CreatedEvent event) {
        if (event.clubId() != null) {
            notifyExerciseUseCase.notifyCreationToClubMembers(
                new ExerciseId(event.exerciseId()),
                new ClubId(event.clubId()),
                new ExerciseTitle(event.exerciseTitle()),
                new ExerciseLocation(event.exerciseLocation()),
                new ExerciseTime(event.exerciseStartTime(), event.exerciseEndTime()),
                new UserId(event.userId())
            );
        }

    }

    @Async
    @EventListener
    public void exerciseDeleted(ExerciseTopic.DeletedEvent event) {
        if (event.isNotifying()) {
            notifyExerciseUseCase.notifyDeletionToMembers(
                new ExerciseId(event.exerciseId()),
                new ExerciseTitle(event.exerciseTitle()),
                new UserId(event.userId())
            );
        }

    }

    @Async
    @EventListener
    public void exerciseUserParticipated(ExerciseTopic.UserParticipatedEvent event) {
        notifyExerciseUseCase.notifyParticipationToAdmin(
            new ExerciseId(event.exerciseId()),
            event.clubId() != null ? new ClubId(event.clubId()) : null,
            new ExerciseTitle(event.exerciseTitle()),
            ExerciseUserStatus.valueOf(event.exerciseUserStatus()),
            new UserId(event.userId())
        );
    }

    @Async
    @EventListener
    public void exerciseUserApplied(ExerciseTopic.UserAppliedEvent event) {
        notifyExerciseUseCase.notifyApplyingToAdmin(
            new ExerciseId(event.exerciseId()),
            event.clubId() != null ? new ClubId(event.clubId()) : null,
            new ExerciseTitle(event.exerciseTitle()),
            ExerciseType.valueOf(event.exerciseType()),
            new UserId(event.userId()),
            new UserId(event.targetUserId())
        );
        notifyExerciseUseCase.notifyApplyingToGuest(
            new ExerciseId(event.exerciseId()),
            event.clubId() != null ? new ClubId(event.clubId()) : null,
            new ExerciseTitle(event.exerciseTitle()),
            ExerciseType.valueOf(event.exerciseType()),
            new UserId(event.userId()),
            new UserId(event.targetUserId())
        );

    }

    @Async
    @EventListener
    public void exerciseUserApproved(ExerciseTopic.UserApprovedEvent event) {
        notifyExerciseUseCase.notifyApprovalToMember(
            new ExerciseId(event.exerciseId()),
            event.clubId() != null ? new ClubId(event.clubId()) : null,
            new ExerciseTitle(event.exerciseTitle()),
            ExerciseUserStatus.valueOf(event.targetExerciseUserStatus()),
            new UserId(event.targetUserId())
        );

    }

    @Async
    @EventListener
    public void exerciseUserCancelled(ExerciseTopic.UserCancelledEvent event) {
        if (event.bySelf()) {
            notifyExerciseUseCase.notifyLeftToAdmin(
                new ExerciseId(event.exerciseId()),
                event.clubId() != null ? new ClubId(event.clubId()) : null,
                new ExerciseTitle(event.exerciseTitle()),
                new UserId(event.userId())
            );
        } else {
            notifyExerciseUseCase.notifyExpellationToMember(
                new ExerciseId(event.exerciseId()),
                event.clubId() != null ? new ClubId(event.clubId()) : null,
                new ExerciseTitle(event.exerciseTitle()),
                new UserId(event.userId())
            );
        }
    }

}
