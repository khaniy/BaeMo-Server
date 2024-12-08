package hotil.baemo.domains.clubs.application.usecases.comment.command;

import hotil.baemo.domains.clubs.application.dto.QClubPostCommentDTO;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostCommentId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.comment.CommentContent;
import hotil.baemo.domains.clubs.domain.value.comment.CommentDepth;
import hotil.baemo.domains.clubs.domain.value.member.UserId;

public interface CreateClubPostCommentUseCase {
    QClubPostCommentDTO.Create create(ClubPostId postId, UserId writerId, ClubPostCommentId preCommentId, CommentDepth commentDepth, CommentContent commentContent);
}
