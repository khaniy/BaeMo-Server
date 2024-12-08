package hotil.baemo.domains.exercise.application.usecases.exercise.command;

import hotil.baemo.domains.exercise.domain.value.ClubExerciseVOGroup;
import hotil.baemo.domains.exercise.domain.value.ExerciseVOGroup;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseThumbnail;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface UpdateExerciseUseCase {

    void updateExercise(UserId userId, ExerciseId exerciseId, ExerciseVOGroup exerciseVOGroup);

    void updateClubExercise(UserId userId, ExerciseId exerciseId, ClubExerciseVOGroup clubExerciseVOGroup);

    void updateThumbnail(UserId userId, ExerciseId exerciseId, ExerciseThumbnail thumbnail);
}
