package hotil.baemo.domains.comment.application.usecases;

import hotil.baemo.domains.comment.application.dto.RetrieveComment;
import hotil.baemo.domains.comment.domain.entity.CommentCommunityId;
import org.springframework.data.domain.Pageable;

public interface QueryCommentUseCase {
    RetrieveComment.CommentDetailsList retrieveCommentListByCommunity(CommentCommunityId communityId, Pageable pageable);
}
