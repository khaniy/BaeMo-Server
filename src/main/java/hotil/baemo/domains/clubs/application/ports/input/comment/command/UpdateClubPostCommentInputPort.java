package hotil.baemo.domains.clubs.application.ports.input.comment.command;

import hotil.baemo.domains.clubs.application.ports.output.comment.CommandClubPostCommentOutPort;
import hotil.baemo.domains.clubs.application.ports.output.comment.QueryClubPostCommentOutputPort;
import hotil.baemo.domains.clubs.application.usecases.comment.command.UpdateClubPostCommentUseCase;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostCommentId;
import hotil.baemo.domains.clubs.domain.policy.comment.UpdateClubPostCommentPolicy;
import hotil.baemo.domains.clubs.domain.value.comment.CommentContent;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateClubPostCommentInputPort implements UpdateClubPostCommentUseCase {
    private final QueryClubPostCommentOutputPort queryClubPostCommentOutputPort;
    private final CommandClubPostCommentOutPort commandClubPostCommentOutPort;

    @Override
    public void update(ClubPostCommentId clubPostCommentId, UserId userId, CommentContent newContent) {
        UpdateClubPostCommentPolicy.execute(clubPostCommentId, userId)
            .valid(queryClubPostCommentOutputPort::load)
            .update(newContent)
            .save(commandClubPostCommentOutPort::save);
    }
}