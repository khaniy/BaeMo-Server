package hotil.baemo.domains.comment.application.ports.input;

import hotil.baemo.domains.comment.application.dto.RetrieveComment;
import hotil.baemo.domains.comment.application.ports.output.QueryCommentOutputPort;
import hotil.baemo.domains.comment.application.usecases.QueryCommentUseCase;
import hotil.baemo.domains.comment.domain.entity.CommentCommunityId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QueryCommentInputPort implements QueryCommentUseCase {
    private final QueryCommentOutputPort queryCommentOutputPort;

    @Override
    public RetrieveComment.CommentDetailsList retrieveCommentListByCommunity(CommentCommunityId communityId, Pageable pageable) {
        return queryCommentOutputPort.retrieveCommentListByCommunity(communityId, pageable);
    }
}