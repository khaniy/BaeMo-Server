package hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.repository;

import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.ExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.repository.impl.ExerciseQRepository;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import org.springframework.data.repository.CrudRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface ExerciseRepository extends CrudRepository<ExerciseEntity, Long> , ExerciseQRepository {

//    List<ExerciseEntity> findAllByEndTime(ZonedDateTime time);
//
//    List<ExerciseEntity> findAllActiveClubExercises(Long clubId);
//
//    List<ExerciseEntity> findAllActiveExercisesByUserId(Long userId);
//
//    List<ExerciseEntity> findAllActiveClubExercisesByUserId(ClubId clubId, UserId userId);
}

