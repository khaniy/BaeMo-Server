package hotil.baemo.domains.clubs.adapter.post.output.persistence.repository.jpa;

import hotil.baemo.domains.clubs.adapter.post.output.persistence.entity.ClubsPostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubsPostLikeJpaRepository extends JpaRepository<ClubsPostLikeEntity, Long> {

    boolean existsByClubsUserIdAndClubsPostId(Long clubsUserId, Long clubsPostId);

    ClubsPostLikeEntity findByClubsUserIdAndClubsPostId(Long clubsUserId, Long clubsPostId);
}
