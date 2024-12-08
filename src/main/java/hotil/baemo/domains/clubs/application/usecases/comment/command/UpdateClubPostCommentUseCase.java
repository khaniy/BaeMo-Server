package hotil.baemo.domains.clubs.application.usecases.comment.command;

import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostCommentId;
import hotil.baemo.domains.clubs.domain.value.comment.CommentContent;
import hotil.baemo.domains.clubs.domain.value.member.UserId;

public interface UpdateClubPostCommentUseCase {
    void update(ClubPostCommentId clubPostCommentId, UserId userId, CommentContent newContent);
}