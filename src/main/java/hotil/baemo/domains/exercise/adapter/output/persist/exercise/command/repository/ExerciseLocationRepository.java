package hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.repository;

import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.ExerciseLocationEntity;
import org.springframework.data.repository.CrudRepository;

public interface ExerciseLocationRepository extends CrudRepository<ExerciseLocationEntity, Long> {
    void deleteByExerciseId(Long exerciseId);
}
