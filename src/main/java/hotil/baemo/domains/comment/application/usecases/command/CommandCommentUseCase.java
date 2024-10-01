package hotil.baemo.domains.comment.application.usecases.command;

import hotil.baemo.domains.comment.domain.entity.CommentCommunityId;
import hotil.baemo.domains.comment.domain.entity.CommentId;
import hotil.baemo.domains.comment.domain.entity.CommentWriter;
import hotil.baemo.domains.comment.domain.entity.PreCommentId;
import hotil.baemo.domains.comment.domain.value.CommentContent;
import hotil.baemo.domains.users.adapter.output.persistence.entity.UsersEntity;

public interface CommandCommentUseCase {
    CommentId create(CommentCommunityId communityId, PreCommentId preCommentId, CommentContent commentContent, CommentWriter commentWriter);

    void update(CommentId commentId, CommentContent newContent,CommentWriter commentWriter);

    void delete(CommentId commentId, CommentWriter commentWriter);
}
