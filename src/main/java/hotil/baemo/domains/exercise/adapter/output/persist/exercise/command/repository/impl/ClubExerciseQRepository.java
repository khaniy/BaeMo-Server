package hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.repository.impl;

import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.ClubExerciseEntity;

import java.util.Optional;

public interface ClubExerciseQRepository {

    Optional<ClubExerciseEntity> findClubExercise(Long exerciseId);

}
