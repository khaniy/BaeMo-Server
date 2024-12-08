package hotil.baemo.support.domain.community;

import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityEntity;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityJpaRepository;
import hotil.baemo.domains.community.domain.value.CommunityCategory;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunitySupport extends FixtureMonkeyBaseSupport {

    @Autowired
    private CommunityJpaRepository communityJpaRepository;

    public Long create(final Long writer) {
        return communityJpaRepository.save(monkey.giveMeBuilder(CommunityEntity.class)
                .setNull("communityId")
                .set("writer", writer)
                .set("isDelete", false)
                .sample())
            .getCommunityId();
    }

    public Long create(final Long writer, final CommunityCategory communityCategory) {
        return communityJpaRepository.save(monkey.giveMeBuilder(CommunityEntity.class)
                .setNull("communityId")
                .set("writer", writer)
                .set("isDelete", false)
                .set("communityCategory", communityCategory)
                .sample())
            .getCommunityId();
    }
}