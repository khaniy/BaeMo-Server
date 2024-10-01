package hotil.baemo.domains.comment.application.ports.output;

import hotil.baemo.domains.comment.domain.entity.CommentId;
import hotil.baemo.domains.comment.domain.entity.CommentWriter;

public interface CommentLikeOutputPort {
    void executeLikeToggle(CommentWriter user, CommentId commentId);
}
