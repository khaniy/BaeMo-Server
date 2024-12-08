package hotil.baemo.domains.clubs.adapter.output.persist.comment.repository;

import hotil.baemo.domains.clubs.adapter.output.persist.comment.dto.RepliesUserDTO;
import hotil.baemo.domains.clubs.application.dto.QClubPostCommentDTO;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;

public interface ClubPostCommentQRepository {

    RepliesUserDTO.SimpleInformationDTO loadUserSimpleInformation(Long repliesWriter);

    ClubMember loadClubsUser(ClubPostId clubPostId, UserId writerId);

    QClubPostCommentDTO.CommentDetailList loadCommentList(UserId userId, ClubPostId postId);
}
