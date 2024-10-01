package hotil.baemo.domains.match.adapter.output.persistence.repository;

import hotil.baemo.domains.match.adapter.output.persistence.entity.MatchEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommandMatchJpaRepository extends JpaRepository<MatchEntity, Long> {

    @Override
    @Query("SELECT m FROM MatchEntity as m " +
        "WHERE m.id = :matchId AND m.isDel = false ")
    Optional<MatchEntity> findById(@Param("matchId") Long matchId);

    @Query("SELECT m FROM MatchEntity as m " +
        "WHERE m.exerciseId = :exerciseId AND m.isDel = false ")
    List<MatchEntity> findAllByExerciseId(@Param("exerciseId") Long exerciseId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m.id, m.matchOrder FROM MatchEntity as m " +
        "WHERE m.exerciseId = :exerciseId AND m.isDel=false ORDER BY m.matchOrder ASC")
    List<Object[]> findMatchOrderByExerciseId(@Param("exerciseId") Long exerciseId);

    @Query("SELECT m FROM MatchEntity as m " +
        "WHERE m.id in :matchIds AND m.isDel = false ")
    List<MatchEntity> findAllByIds(@Param("matchIds") List<Long> matchIds);

    @Query("SELECT m FROM MatchEntity as m " +
        "WHERE m.exerciseId = :exerciseId AND m.matchStatus = 'COMPLETE' AND m.isDel = false ")
    List<MatchEntity> findCompleteMatches(@Param("exerciseId") Long exerciseId);

    @Query("SELECT m FROM MatchEntity as m " +
        "WHERE m.exerciseId in :exerciseIds AND m.isDel = false ")
    List<MatchEntity> findAllByExerciseIds(@Param("exerciseIds") List<Long> exerciseIds);

    @Modifying
    @Query("UPDATE MatchEntity e SET e.matchStatus = 'HISTORY' " +
        "WHERE e.matchStatus = 'COMPLETE' " +
        "AND e.exerciseId IN :exerciseIds " +
        "AND e.isDel = false")
    void updateCompletedMatchToHistoryMatchByExerciseIds(@Param("exerciseIds") List<Long> exerciseIds);

    @Query("SELECT m FROM MatchEntity m " +
        "WHERE m.exerciseId IN :exerciseIds " +
        "AND m.matchStatus != 'COMPLETE' " +
        "AND m.isDel = false")
    List<MatchEntity> findNotCompletedMatchByExerciseIds(@Param("exerciseIds") List<Long> exerciseIds);
}
