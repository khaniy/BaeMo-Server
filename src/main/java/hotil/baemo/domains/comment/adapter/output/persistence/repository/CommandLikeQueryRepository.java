package hotil.baemo.domains.comment.adapter.output.persistence.repository;

import hotil.baemo.domains.comment.domain.entity.CommentId;
import hotil.baemo.domains.comment.domain.entity.CommentWriter;

public interface CommandLikeQueryRepository {
    boolean existsByUserIdAndCommentId(CommentWriter user, CommentId commentId);
}
