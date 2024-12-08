package hotil.baemo.domains.exercise.adapter.output.persist.court.repository;

import hotil.baemo.domains.exercise.adapter.output.persist.court.entity.ExerciseCourtEntity;
import jakarta.persistence.LockModeType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExerciseCourtRepository extends CrudRepository<ExerciseCourtEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<ExerciseCourtEntity> findAllByExerciseId(Long exerciseId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ExerciseCourtEntity> findById(Long id);

    void deleteAllByExerciseId(Long exerciseId);

}
