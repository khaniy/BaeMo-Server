package hotil.baemo.domains.exercise.application.usecases.exercise.command;

import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface UpdateExerciseUserUseCase {

    void participateExercise(UserId user, ExerciseId exerciseId);

    void applyExerciseGuest(ExerciseId exerciseId, UserId userId, UserId targetUserId);

    void approveGuestUser(ExerciseId exerciseId, UserId userId, UserId targetUserId);

    void approvePendingUser(UserId userId, ExerciseId exerciseId, UserId targetUserId);

    void expelExercise(UserId userId, ExerciseId exerciseId, UserId targetUserId);

    void selfExpelExercise(UserId userId, ExerciseId exerciseId);
}
