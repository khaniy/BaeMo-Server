package hotil.baemo.domains.exercise.application.usecases.user.command;


import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface RejectPendingUserUseCase {

    void rejectPendingMember(ExerciseId exerciseId, UserId userId, UserId targetUserId);
    void rejectPendingGuest(ExerciseId exerciseId, UserId userId, UserId targetUserId);
}
