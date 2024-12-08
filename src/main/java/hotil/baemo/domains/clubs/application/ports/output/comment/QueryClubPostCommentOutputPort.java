package hotil.baemo.domains.clubs.application.ports.output.comment;

import hotil.baemo.domains.clubs.application.dto.QClubPostCommentDTO;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostComment;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostCommentId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;

public interface QueryClubPostCommentOutputPort {
    ClubPostComment load(ClubPostCommentId clubPostCommentId);

    QClubPostCommentDTO.CommentDetailList loadRepliesDetailList(UserId userId, ClubPostId clubPostId);

    ClubMember loadClubsUser(ClubPostId clubPostId, UserId writerId);

}
