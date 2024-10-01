package hotil.baemo.domains.exercise.adapter.output.persist.repository;

import hotil.baemo.domains.exercise.adapter.output.persist.entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface ExerciseJpaRepository extends JpaRepository<ExerciseEntity, Long> {
    @Override
    @Query("SELECT e from ExerciseEntity e WHERE e.id=:exerciseId and e.isDel=false ")
    Optional<ExerciseEntity> findById(@Param("exerciseId") Long exerciseId);

    @Query("SELECT e from ExerciseEntity e WHERE e.exerciseStartTime<=:now and e.exerciseStatus!='COMPLETE' and e.isDel=false ")
    List<ExerciseEntity> findAllByStartTime(@Param("now") ZonedDateTime now);

    @Query("SELECT e from ExerciseEntity e WHERE e.exerciseEndTime<=:now and e.exerciseStatus!='COMPLETE' and e.isDel=false ")
    List<ExerciseEntity> findAllByEndTime(@Param("now") ZonedDateTime now);

}
