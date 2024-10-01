package hotil.baemo.domains.comment.application.ports.output.valid;

import hotil.baemo.domains.comment.domain.entity.CommentId;
import hotil.baemo.domains.comment.domain.entity.CommentWriter;

public interface ValidCommentOutputPort {
    void validAuthority(CommentWriter commentWriter, CommentId commentId);

    void validNotAuthor(CommentWriter commentWriter, CommentId commentId);

    void validStatus(CommentId commentId);
}
