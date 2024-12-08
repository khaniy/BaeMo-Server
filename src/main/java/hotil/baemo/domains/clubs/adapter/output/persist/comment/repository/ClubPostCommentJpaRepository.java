package hotil.baemo.domains.clubs.adapter.output.persist.comment.repository;

import hotil.baemo.domains.clubs.adapter.output.persist.comment.entity.ClubPostCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubPostCommentJpaRepository extends JpaRepository<ClubPostCommentEntity, Long> {
}
