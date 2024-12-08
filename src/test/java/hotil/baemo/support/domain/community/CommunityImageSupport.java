package hotil.baemo.support.domain.community;

import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityImageEntity;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityImageJpaRepository;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityImageSupport extends FixtureMonkeyBaseSupport {
    @Autowired
    private CommunityImageJpaRepository communityImageJpaRepository;

    public CommunityImageEntity create(Long communityId, Boolean isThumbnail) {
        return communityImageJpaRepository.save(monkey.giveMeBuilder(CommunityImageEntity.class)
            .setNull("communityImageId")
            .set("communityId", communityId)
            .set("isDelete", false)
            .set("isThumbnail", isThumbnail)
            .sample()
        );
    }
}