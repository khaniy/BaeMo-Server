package hotil.baemo.domains.clubs.application.ports.output.comment;

import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostCommentId;

public interface CommandClubPostCommentLikeOutPort {
    void executeLike(ClubPostCommentId clubPostCommentId, UserId userId);
}
