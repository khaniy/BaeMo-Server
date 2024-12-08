package hotil.baemo.support.domain.club;

import hotil.baemo.domains.clubs.adapter.output.persist.post.entity.ClubsPostLikeEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.post.repository.ClubsPostLikeJpaRepository;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClubPostLikeSupport extends FixtureMonkeyBaseSupport {
    @Autowired
    private ClubsPostLikeJpaRepository clubsPostLikeJpaRepository;

    public void save(Long postId, Long userId) {
        clubsPostLikeJpaRepository.save(
            monkey.giveMeBuilder(ClubsPostLikeEntity.class)
                .setNull("id")
                .set("clubsUserId", userId)
                .set("clubsPostId", postId)
                .set("isLike", true)
                .sample()
        );
    }
}
