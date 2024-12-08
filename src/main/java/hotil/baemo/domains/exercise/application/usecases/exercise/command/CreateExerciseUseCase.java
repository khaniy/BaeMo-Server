package hotil.baemo.domains.exercise.application.usecases.exercise.command;

import hotil.baemo.domains.exercise.domain.value.ClubExerciseVOGroup;
import hotil.baemo.domains.exercise.domain.value.ExerciseVOGroup;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface CreateExerciseUseCase {

    void createExercise(UserId userId, ExerciseVOGroup exerciseVOGroup);

    void createClubExercise(UserId userId, ClubId clubId, ClubExerciseVOGroup clubExerciseVOGroup);
}
