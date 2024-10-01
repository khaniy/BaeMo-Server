package hotil.baemo.domains.community.adapter.output.persistence;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityStatsEntity;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityJpaRepository;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityStatsJpaRepository;
import hotil.baemo.domains.community.application.ports.output.CommunityStatsOutputPort;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityStatsOutputAdapter implements CommunityStatsOutputPort {
    private final CommunityStatsJpaRepository communityStatsJpaRepository;
    private final CommunityJpaRepository communityJpaRepository;

    @Override
    public void incrementViewCount(CommunityId communityId, CommunityUserId communityUserId) {
        // TODO : 시간 정책
        final var communityIdx = communityId.id();
        final var userId = communityUserId.id();

        checkExistsCommunity(communityIdx);

        final var communityStatsEntity = loadCommunityStatsEntity(communityId, communityIdx, userId);

        communityStatsEntity.incrementViewCount();
        communityStatsJpaRepository.save(communityStatsEntity);
    }

    @Override
    public void executeLike(CommunityId communityId, CommunityUserId communityUserId) {
        final var communityIdx = communityId.id();
        final var userId = communityUserId.id();

        checkExistsCommunity(communityIdx);

        final var communityStatsEntity = loadCommunityStatsEntity(communityId, communityIdx, userId);

        communityStatsEntity.likeToggle();
        communityStatsJpaRepository.save(communityStatsEntity);
    }

    private CommunityStatsEntity loadCommunityStatsEntity(CommunityId communityId, Long communityIdx, Long userId) {
        return communityStatsJpaRepository.findByCommunityIdAndUserId(communityIdx, userId)
                .or(() -> Optional.of(CommunityStatsEntity.builder()
                        .communityId(communityId.id())
                        .userId(userId)
                        .build()))
                .get();
    }

    private void checkExistsCommunity(Long communityIdx) {
        if (!communityJpaRepository.existsById(communityIdx)) {
            throw new CustomException(ResponseCode.COMMUNITY_NOT_FOUND);
        }
    }
}