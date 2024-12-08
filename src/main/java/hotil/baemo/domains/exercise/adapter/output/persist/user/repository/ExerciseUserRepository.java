package hotil.baemo.domains.exercise.adapter.output.persist.user.repository;

import hotil.baemo.domains.exercise.adapter.output.persist.user.entity.ExerciseUserEntity;
import jakarta.persistence.LockModeType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExerciseUserRepository extends CrudRepository<ExerciseUserEntity, Long> {

    Optional<ExerciseUserEntity> findByUserIdAndExerciseId(Long userId, Long exerciseId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<ExerciseUserEntity> findAllByExerciseId(Long exerciseId);

    @Modifying
    @Query("DELETE FROM ExerciseUserEntity WHERE exerciseId in :exerciseIds")
    void deleteAllByExerciseIds(@Param("exerciseIds") List<Long> exerciseIds);

    void deleteAllByExerciseId(Long id);

    List<ExerciseUserEntity> findByExerciseIdAndUserIdIn(Long exerciseId, List<Long> userIds);
}
