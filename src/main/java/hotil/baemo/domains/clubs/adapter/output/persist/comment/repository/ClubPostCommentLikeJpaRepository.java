package hotil.baemo.domains.clubs.adapter.output.persist.comment.repository;

import hotil.baemo.domains.clubs.adapter.output.persist.comment.entity.ClubPostCommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubPostCommentLikeJpaRepository extends JpaRepository<ClubPostCommentLikeEntity, Long> {
    boolean existsByCommentIdAndClubsUserId(Long commendId, Long clubsUserId);
    Optional<ClubPostCommentLikeEntity> findByCommentIdAndClubsUserId(Long commentId, Long clubsUserId);
}
