package hotil.baemo.domains.clubs.adapter.output.persist.post.repository;

import hotil.baemo.domains.clubs.adapter.output.persist.post.entity.ClubsPostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubsPostLikeJpaRepository extends JpaRepository<ClubsPostLikeEntity, Long> {

    Optional<ClubsPostLikeEntity> findByClubsUserIdAndClubsPostId(Long clubsUserId, Long clubsPostId);
}
