package hotil.baemo.domains.clubs.application.ports.input.comment.command;

import hotil.baemo.domains.clubs.application.ports.output.comment.CommandClubPostCommentLikeOutPort;
import hotil.baemo.domains.clubs.application.usecases.comment.command.LikeClubPostCommentUseCase;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostCommentId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeClubPostCommentInputPort implements LikeClubPostCommentUseCase {
    private final CommandClubPostCommentLikeOutPort commandClubPostCommentLikeOutPort;

    @Override
    public void like(ClubPostCommentId clubPostCommentId, UserId userId) {
        commandClubPostCommentLikeOutPort.executeLike(clubPostCommentId, userId);
    }
}