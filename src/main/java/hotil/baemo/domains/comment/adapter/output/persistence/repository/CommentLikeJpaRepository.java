package hotil.baemo.domains.comment.adapter.output.persistence.repository;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.comment.adapter.output.persistence.entity.CommentLikeEntity;
import hotil.baemo.domains.comment.domain.entity.CommentId;
import hotil.baemo.domains.comment.domain.entity.CommentWriter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeJpaRepository extends JpaRepository<CommentLikeEntity, Long> {

    Optional<CommentLikeEntity> findByUserIdAndCommentId(Long userId, Long commentId);

    default CommentLikeEntity load(CommentWriter user, CommentId commentId) {
        return findByUserIdAndCommentId(user.id(), commentId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.COMMENT_NOT_FOUND));
    }
}