package hotil.baemo.domains.community.adapter.output.persistence.repository;

import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityStatsJpaRepository extends JpaRepository<CommunityStatsEntity, Long> {
    Optional<CommunityStatsEntity> findByUserId(Long userId);

    Optional<CommunityStatsEntity> findByCommunityIdAndUserId(Long communityId, Long userId);

    boolean existsByCommunityIdAndUserId(Long communityId, Long userId);
}
