package hotil.baemo.domains.comment.application.ports.input;

import hotil.baemo.domains.comment.application.ports.output.CommandCommentOutputPort;
import hotil.baemo.domains.comment.application.ports.output.valid.ValidCommentOutputPort;
import hotil.baemo.domains.comment.application.usecases.command.CommandCommentUseCase;
import hotil.baemo.domains.comment.domain.entity.CommentCommunityId;
import hotil.baemo.domains.comment.domain.entity.CommentId;
import hotil.baemo.domains.comment.domain.entity.CommentWriter;
import hotil.baemo.domains.comment.domain.entity.PreCommentId;
import hotil.baemo.domains.comment.domain.value.CommentContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommandCommentInputPort implements CommandCommentUseCase {
    private final CommandCommentOutputPort commandCommentOutputPort;
    private final ValidCommentOutputPort validCommentOutputPort;

    @Override
    public CommentId create(CommentCommunityId communityId, PreCommentId preCommentId, CommentContent commentContent, CommentWriter commentWriter) {
        return commandCommentOutputPort.save(communityId, preCommentId, commentContent, commentWriter);
    }

    @Override
    public void update(CommentId commentId, CommentContent newCommentContent, CommentWriter commentWriter) {
        validCommentOutputPort.validAuthority(commentWriter, commentId);
        commandCommentOutputPort.update(commentId, newCommentContent, commentWriter);
    }

    @Override
    public void delete(CommentId commentId, CommentWriter commentWriter) {
        validCommentOutputPort.validAuthority(commentWriter, commentId);
        commandCommentOutputPort.delete(commentId, commentWriter);
    }
}