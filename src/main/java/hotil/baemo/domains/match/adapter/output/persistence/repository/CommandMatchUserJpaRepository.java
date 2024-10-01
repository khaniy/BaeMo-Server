package hotil.baemo.domains.match.adapter.output.persistence.repository;

import hotil.baemo.domains.match.adapter.output.persistence.entity.MatchUserEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandMatchUserJpaRepository extends JpaRepository<MatchUserEntity, Long> {
    @Query("SELECT e FROM MatchUserEntity e WHERE e.matchId = :matchId")
    List<MatchUserEntity> findAllByMatchId(Long matchId);

    @Modifying
    @Query("DELETE FROM MatchUserEntity e WHERE e.matchId = :matchId")
    void deleteByMatchId(@Param("matchId") Long matchId);

    @Modifying
    @Query("DELETE FROM MatchUserEntity e WHERE e.matchId in :matchIdList")
    void deleteAllByMatches(@Param("matchIdList") List<Long> matchIdList);

    @Modifying
    @Query("DELETE FROM MatchUserEntity e WHERE e.matchId in :matchIds and e.userId = :userId")
    void deleteAllByMatchesAndExercise(@Param("matchIds") List<Long> matchIds, @Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM MatchUserEntity e WHERE e.exerciseId = :exerciseId")
    void deleteAllByExerciseId(@Positive @NotNull Long exerciseId);
}
