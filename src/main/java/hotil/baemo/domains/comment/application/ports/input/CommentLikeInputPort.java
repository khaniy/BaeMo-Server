package hotil.baemo.domains.comment.application.ports.input;

import hotil.baemo.domains.comment.application.ports.output.CommentLikeOutputPort;
import hotil.baemo.domains.comment.application.ports.output.valid.ValidCommentOutputPort;
import hotil.baemo.domains.comment.application.usecases.command.CommentLikeUseCase;
import hotil.baemo.domains.comment.domain.entity.CommentId;
import hotil.baemo.domains.comment.domain.entity.CommentWriter;
import hotil.baemo.domains.comment.domain.policy.LikeCommentPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentLikeInputPort implements CommentLikeUseCase {
    private final CommentLikeOutputPort commentLikeOutputPort;
    private final ValidCommentOutputPort validCommentOutputPort;

    @Override
    public void like(CommentWriter user, CommentId commentId) {
        LikeCommentPolicy.execute(user, commentId)
            .validAuthority(validCommentOutputPort::validNotAuthor)
            .validStatus(validCommentOutputPort::validStatus)
            .likeToggle(commentLikeOutputPort::executeLikeToggle);
    }
}