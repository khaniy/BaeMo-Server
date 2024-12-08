package hotil.baemo.domains.clubs.application.usecases.comment.command;

import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostCommentId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;

public interface DeleteClubPostCommentUseCase {

    void delete(ClubPostCommentId clubPostCommentId, UserId userId);
}