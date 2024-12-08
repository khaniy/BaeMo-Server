package hotil.baemo.domains.community.adapter.output.persistence.repository;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityLikeEntity;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityLikeJpaRepository extends JpaRepository<CommunityLikeEntity, Long> {
    Optional<CommunityLikeEntity> findByUserIdAndCommunityId(Long userId, Long communityId);

    default CommunityLikeEntity load(CommunityId communityId, CommunityUserId communityUserId) {
        return findByUserIdAndCommunityId(communityId.id(), communityUserId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.COMMUNITY_NOT_FOUND));
    }

    default Optional<CommunityLikeEntity> loadOptional(CommunityId communityId, CommunityUserId communityUserId) {
        return findByUserIdAndCommunityId(communityId.id(), communityUserId.id());
    }
}