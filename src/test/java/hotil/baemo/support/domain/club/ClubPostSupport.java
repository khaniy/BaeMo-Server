package hotil.baemo.support.domain.club;

import hotil.baemo.domains.clubs.adapter.output.persist.post.entity.ClubsPostEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.post.repository.ClubsPostJpaRepository;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostType;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClubPostSupport extends FixtureMonkeyBaseSupport {
    @Autowired
    private ClubsPostJpaRepository clubsPostJpaRepository;

    public Long createPost(final Long clubsId, final ClubPostType type) {
        return this.clubsPostJpaRepository.save(monkey.giveMeBuilder(ClubsPostEntity.class)
            .set("clubsId", clubsId)
            .set("isDelete", false)
            .set("ClubsPostType", type)
            .sample()
        ).getClubsPostId();
    }

    public Long createPost(final Long clubsId) {
        return this.clubsPostJpaRepository.save(monkey.giveMeBuilder(ClubsPostEntity.class)
            .set("clubsId", clubsId)
            .set("isDelete", false)
            .sample()
        ).getClubsPostId();
    }

    public Long createPost(final Long clubsId, final Long writer) {
        return this.clubsPostJpaRepository.save(monkey.giveMeBuilder(ClubsPostEntity.class)
            .set("clubsId", clubsId)
            .set("clubsPostWriter", writer)
            .set("isDelete", false)
            .sample()
        ).getClubsPostId();
    }
}
