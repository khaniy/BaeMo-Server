package hotil.baemo.domains.exercise.adapter.output.persist.repository;

import hotil.baemo.domains.exercise.adapter.output.persist.entity.ExerciseUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExerciseUserJpaRepository extends JpaRepository<ExerciseUserEntity, Long> {

    @Query("SELECT e FROM ExerciseUserEntity e " +
        "WHERE e.userId = :userId AND e.exerciseId = :exerciseId AND e.isDel = false")
    Optional<ExerciseUserEntity> findByUserIdAndExerciseId(@Param("userId") Long userId, @Param("exerciseId") Long exerciseId);

    @Query("SELECT e FROM ExerciseUserEntity e " +
        "WHERE e.exerciseId = :exerciseId AND e.isDel = false")
    List<ExerciseUserEntity> findByExerciseId(@Param("exerciseId") Long exerciseId);

    @Modifying
    @Query("DELETE FROM ExerciseUserEntity e  WHERE e.userId = :userId AND e.isDel = false ")
    void deleteAllByUserId(@Param("userId") Long userId);

    @Query("SELECT e FROM ExerciseUserEntity e " +
        "WHERE e.exerciseId in :exerciseIds AND e.isDel = false")
    List<ExerciseUserEntity> findByExerciseIds(@Param("exerciseIds") List<Long> exerciseIds);

    @Modifying
    @Query("DELETE FROM ExerciseUserEntity e " +
        "WHERE e.exerciseId in :exerciseIds AND e.userId = :userId AND e.isDel = false")
    void deleteAllByExerciseIdsAndUserId(@Param("exerciseIds") List<Long> exerciseIds, @Param("userId") Long userId);
}
