package hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.repository.impl;

import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.ExerciseEntity;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface ExerciseQRepository {

    List<ExerciseEntity> findAllByEndTime(ZonedDateTime time);

    List<ExerciseEntity> findAllActiveClubExercises(ClubId clubId);

    List<ExerciseEntity> findAllActiveExercisesByUserId(UserId userId);

    List<ExerciseEntity> findAllActiveClubExercisesByUserId(ClubId clubId, UserId userId);
}
