package hotil.baemo.domains.comment.application.ports.output;

import hotil.baemo.domains.comment.application.dto.RetrieveComment;
import hotil.baemo.domains.comment.domain.entity.CommentCommunityId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface QueryCommentOutputPort {
    RetrieveComment.CommentDetailsList retrieveCommentListByCommunity(CommentCommunityId communityId, Pageable pageable);
}
