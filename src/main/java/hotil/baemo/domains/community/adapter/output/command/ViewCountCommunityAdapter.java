package hotil.baemo.domains.community.adapter.output.command;

import hotil.baemo.core.redis.BaemoRedis;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityJpaRepository;
import hotil.baemo.domains.community.application.ports.output.command.ViewCountCommunityOutputPort;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Transactional
@RequiredArgsConstructor
public class ViewCountCommunityAdapter implements ViewCountCommunityOutputPort {
    private static final String COMMUNITY_VIEW_COUNT = "COMMUNITY_VIEW_COUNT";
    private final CommunityJpaRepository communityJpaRepository;
    private final BaemoRedis baemoRedis;

    @Override
    public void incrementViewCount(CommunityId communityId, CommunityUserId communityUserId) {
        final var viewCountKey = getViewCountKey(communityId, communityUserId);

        if (baemoRedis.notExists(viewCountKey)) {
            baemoRedis.set(viewCountKey, COMMUNITY_VIEW_COUNT, Duration.ofHours(3L));
            communityJpaRepository.loadById(communityId)
                .incrementViewCount();
        }
    }

    private String getViewCountKey(CommunityId communityId, CommunityUserId communityUserId) {
        return COMMUNITY_VIEW_COUNT + ":COMMUNITY:" + communityId.id() + ":USER:" + communityUserId.id();
    }
}