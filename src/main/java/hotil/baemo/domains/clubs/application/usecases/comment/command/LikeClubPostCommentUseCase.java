package hotil.baemo.domains.clubs.application.usecases.comment.command;

import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostCommentId;

public interface LikeClubPostCommentUseCase {
    void like(ClubPostCommentId clubPostCommentId, UserId userId);
}
