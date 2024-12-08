package hotil.baemo.domains.clubs.application.usecases.comment.query;

import hotil.baemo.domains.clubs.application.dto.QClubPostCommentDTO;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;

public interface RetrieveClubPostCommentUseCase {
    QClubPostCommentDTO.CommentDetailList retrieve(UserId userId, ClubPostId postId);
}
