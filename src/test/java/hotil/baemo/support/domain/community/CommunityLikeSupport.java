package hotil.baemo.support.domain.community;

import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityLikeEntity;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityLikeJpaRepository;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityLikeSupport extends FixtureMonkeyBaseSupport {

    @Autowired
    private CommunityLikeJpaRepository communityLikeJpaRepository;

    public CommunityLikeEntity save(final Long communityId, final Long userId) {
        return communityLikeJpaRepository.save(
            monkey.giveMeBuilder(CommunityLikeEntity.class)
                .set("communityId", communityId)
                .set("userId", userId)
                .sample()
        );
    }
}
