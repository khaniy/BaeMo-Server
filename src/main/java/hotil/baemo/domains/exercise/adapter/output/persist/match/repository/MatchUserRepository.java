package hotil.baemo.domains.exercise.adapter.output.persist.match.repository;

import hotil.baemo.domains.exercise.adapter.output.persist.match.entity.MatchUserEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MatchUserRepository extends CrudRepository<MatchUserEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<MatchUserEntity> findAllByMatchId(Long matchId);

    void deleteAllByMatchId(Long matchId);

    void deleteAllByUserIdAndExerciseId(Long userId, Long exerciseId);

    void deleteAllByExerciseId(Long exerciseId);

    List<MatchUserEntity> findAllByUserIdInAndExerciseId(List<Long> userId, Long exerciseId);
}
