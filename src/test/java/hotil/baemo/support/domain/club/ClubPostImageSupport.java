package hotil.baemo.support.domain.club;

import hotil.baemo.domains.clubs.adapter.output.persist.post.entity.ClubsPostImageEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.post.repository.ClubsPostImageJpaRepository;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClubPostImageSupport extends FixtureMonkeyBaseSupport {

    @Autowired
    private ClubsPostImageJpaRepository clubsPostImageJpaRepository;

    public Long saveImage(final Long clubsPostId) {
        return clubsPostImageJpaRepository.save(monkey
                .giveMeBuilder(ClubsPostImageEntity.class)
                .set("clubsPostId", clubsPostId)
                .set("isDeleted", false)
                .set("isThumbnail", false)
                .sample())
            .getClubsPostImageId();
    }

    public Long saveImage(final Long clubsPostId, final Long orderNumber, final Boolean isThumbnail) {
        return clubsPostImageJpaRepository.save(monkey
                .giveMeBuilder(ClubsPostImageEntity.class)
                .set("clubsPostId", clubsPostId)
                .set("isDeleted", false)
                .set("orderNumber", orderNumber)
                .set("isThumbnail", isThumbnail)
                .sample())
            .getClubsPostImageId();
    }
}