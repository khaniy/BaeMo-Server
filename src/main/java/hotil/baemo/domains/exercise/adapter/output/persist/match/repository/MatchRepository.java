package hotil.baemo.domains.exercise.adapter.output.persist.match.repository;

import hotil.baemo.domains.exercise.adapter.output.persist.match.entity.MatchEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.match.repository.impl.MatchQRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchRepository extends CrudRepository<MatchEntity, Long>, MatchQRepository {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<MatchEntity> findAllByExerciseId(@Param("exerciseId") Long exerciseId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<MatchEntity> findAllByIdIn(List<Long> matchIds);
}
