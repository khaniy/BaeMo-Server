package hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.repository;

import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.ClubExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.repository.impl.ClubExerciseQRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClubExerciseRepository extends CrudRepository<ClubExerciseEntity, Long> , ClubExerciseQRepository {
}
