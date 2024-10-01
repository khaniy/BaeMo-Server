package hotil.baemo.domains.notification.application.usecase;

import hotil.baemo.domains.notification.domains.value.exercise.*;
import hotil.baemo.domains.notification.domains.value.user.UserId;

public interface NotifyExerciseUseCase {

    void notifyCreationToClubUsers(ExerciseId exerciseId, ExerciseTitle exerciseTitle, ExerciseLocation exerciseLocation, ExerciseTime time, UserId createUserId);

    void notifyDeletionToExerciseUsers(ExerciseId exerciseId, ExerciseTitle exerciseTitle, UserId deleteUserId);

    void notifyParticipationToExerciseUsers(ExerciseId exerciseId, ExerciseTitle exerciseTitle, ExerciseUserStatus exerciseUserStatus, UserId participantUserId);

    void notifyApplyingToExerciseAdminUsers(ExerciseId exerciseId, ExerciseTitle exerciseTitle, ExerciseType exerciseType, UserId applyUserId, UserId targetUserId);

    void notifyApprovalToExerciseUser(ExerciseId exerciseId, ExerciseTitle exerciseTitle, ExerciseUserStatus exerciseUserStatus, UserId approverUserId);

    void notifyCancellationToExerciseAdminUsers(ExerciseId exerciseId, ExerciseTitle exerciseTitle, UserId cancelUserId);

    void notifyExpellationToExerciseUser(ExerciseId exerciseId, ExerciseTitle exerciseTitle, UserId cancelUserId);
}
