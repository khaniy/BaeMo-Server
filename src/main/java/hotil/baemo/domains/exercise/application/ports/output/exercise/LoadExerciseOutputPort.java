package hotil.baemo.domains.exercise.application.ports.output.exercise;

import hotil.baemo.domains.exercise.domain.entity.exercise.ClubExercise;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

import java.time.ZonedDateTime;
import java.util.List;

public interface LoadExerciseOutputPort {

    ClubExercise loadClubExercise(ExerciseId exerciseId);

    Exercise loadExercise(ExerciseId exerciseId);

    List<Exercise> loadAllActiveExercises(UserId userId);

    List<Exercise> loadAllActiveClubExercises(ClubId clubId);

    List<Exercise> loadAllActiveClubExercisesByUserId(ClubId clubId, UserId userId);

    List<Exercise> loadExerciseByEndTime(ZonedDateTime zonedDateTime);
}
