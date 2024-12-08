package hotil.baemo.domains.exercise.application.usecases.user.command;

import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface ExpelExerciseUserUseCase {

    void expelExercise(ExerciseId exerciseId, UserId userId, UserId targetUserId);

    void leaveExercise(ExerciseId exerciseId, UserId userId);

    void expelAllActiveExercises(UserId userId);

    void expelAllActiveClubExercises(ClubId clubId, UserId userId);
}
