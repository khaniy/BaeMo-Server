package hotil.baemo.domains.comment.application.ports.output;

import hotil.baemo.domains.comment.application.dto.RetrieveComment;
import hotil.baemo.domains.comment.domain.entity.CommentCommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import org.springframework.data.domain.Pageable;

public interface QueryCommentOutputPort {
    RetrieveComment.CommentDetailsList retrieveCommentListByCommunity(CommentCommunityId communityId, CommunityUserId communityUserId, Pageable pageables);
}
