package hotil.baemo.domains.clubs.adapter.replies.output.persistence.repository;

import hotil.baemo.domains.clubs.adapter.replies.output.persistence.entity.StatRepliesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatRepliesEntityJpaRepository extends JpaRepository<StatRepliesEntity, Long> {
    boolean existsByRepliesIdAndClubsUserId(Long repliesId, Long clubsUserId);
    Optional<StatRepliesEntity> findByRepliesIdAndClubsUserId(Long repliesId, Long clubsUserId);
}
