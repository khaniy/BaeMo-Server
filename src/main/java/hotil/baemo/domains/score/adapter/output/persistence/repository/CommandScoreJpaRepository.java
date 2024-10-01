package hotil.baemo.domains.score.adapter.output.persistence.repository;

import hotil.baemo.domains.score.adapter.output.persistence.entity.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommandScoreJpaRepository extends JpaRepository<ScoreEntity, Long> {

    @Query("SELECT e FROM ScoreEntity e WHERE e.matchId= :matchId")
    ScoreEntity findByMatchId(Long matchId);

    @Modifying
    @Query("DELETE FROM ScoreEntity e WHERE e.matchId = :matchId")
    void deleteByMatchId(Long matchId);
}
