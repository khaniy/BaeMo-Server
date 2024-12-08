package hotil.baemo.domains.notification.application.usecase;

import hotil.baemo.domains.notification.domains.value.club.ClubId;
import hotil.baemo.domains.notification.domains.value.exercise.*;
import hotil.baemo.domains.notification.domains.value.user.UserId;

public interface NotifyExerciseUseCase {

    void notifyCreationToClubMembers(ExerciseId exerciseId, ClubId clubId, ExerciseTitle exerciseTitle, ExerciseLocation exerciseLocation, ExerciseTime time, UserId createUserId);

    void notifyExpellationToMember(
        ExerciseId exerciseId,
        ClubId clubId,
        ExerciseTitle exerciseTitle,
        UserId expelledUserId
    );

    void notifyDeletionToMembers(ExerciseId exerciseId, ExerciseTitle exerciseTitle, UserId deleteUserId);

    void notifyApplyingToAdmin(ExerciseId exerciseId, ClubId clubId, ExerciseTitle exerciseTitle, ExerciseType exerciseType, UserId applyUserId, UserId targetUserId);

    void notifyParticipationToAdmin(
        ExerciseId exerciseId,
        ClubId clubId,
        ExerciseTitle exerciseTitle,
        ExerciseUserStatus exerciseUserStatus,
        UserId participantUserId
    );

    void notifyApprovalToMember(
        ExerciseId exerciseId,
        ClubId clubId,
        ExerciseTitle exerciseTitle,
        ExerciseUserStatus exerciseUserStatus,
        UserId approverUserId
    );

    void notifyLeftToAdmin(
        ExerciseId exerciseId,
        ClubId clubId,
        ExerciseTitle exerciseTitle,
        UserId cancelUserId
    );

    void notifyApplyingToGuest(ExerciseId exerciseId, ClubId clubId, ExerciseTitle exerciseTitle, ExerciseType exerciseType, UserId applyUserId, UserId targetUserId);
}
