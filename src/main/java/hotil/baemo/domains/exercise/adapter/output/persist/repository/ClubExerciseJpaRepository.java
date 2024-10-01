package hotil.baemo.domains.exercise.adapter.output.persist.repository;

import hotil.baemo.domains.exercise.adapter.output.persist.entity.ClubExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClubExerciseJpaRepository extends JpaRepository<ClubExerciseEntity, Long> {

    @Override
    @Query("SELECT e from ClubExerciseEntity e WHERE e.id=:exerciseId and e.isDel=false ")
    Optional<ClubExerciseEntity> findById(@Param("exerciseId") Long exerciseId);
}
