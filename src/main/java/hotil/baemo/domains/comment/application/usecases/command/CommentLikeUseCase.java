package hotil.baemo.domains.comment.application.usecases.command;

import hotil.baemo.domains.comment.domain.entity.CommentId;
import hotil.baemo.domains.comment.domain.entity.CommentWriter;

public interface CommentLikeUseCase {
    void like(CommentWriter user, CommentId commentId);
}
