package hotil.baemo.domains.comment.application.ports.output;

import hotil.baemo.domains.comment.domain.entity.CommentCommunityId;
import hotil.baemo.domains.comment.domain.entity.CommentId;
import hotil.baemo.domains.comment.domain.entity.CommentWriter;
import hotil.baemo.domains.comment.domain.entity.PreCommentId;
import hotil.baemo.domains.comment.domain.value.CommentContent;

public interface CommandCommentOutputPort {
    CommentId save(CommentCommunityId communityId, PreCommentId preCommentId, CommentContent commentContent, CommentWriter writer);

    void update(CommentId commentId, CommentContent newCommentContent, CommentWriter commentWriter);

    void delete(CommentId commentId, CommentWriter commentWriter);
}