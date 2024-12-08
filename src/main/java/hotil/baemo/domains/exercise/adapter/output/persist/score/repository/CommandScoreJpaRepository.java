package hotil.baemo.domains.exercise.adapter.output.persist.score.repository;

import hotil.baemo.domains.exercise.adapter.output.persist.score.entity.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommandScoreJpaRepository extends JpaRepository<ScoreEntity, Long> {

    @Query("SELECT e FROM ScoreEntity e WHERE e.matchId= :matchId")
    ScoreEntity findByMatchId(Long matchId);

    @Modifying
    @Query("DELETE FROM ScoreEntity e WHERE e.matchId = :matchId")
    void deleteByMatchId(Long matchId);

    void deleteAllByMatchIdIn(List<Long> matchIds);
}
