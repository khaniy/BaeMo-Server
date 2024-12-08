package hotil.baemo.domains.clubs.application.ports.input.comment.command;

import hotil.baemo.domains.clubs.application.dto.QClubPostCommentDTO;
import hotil.baemo.domains.clubs.application.ports.output.comment.CommandClubPostCommentOutPort;
import hotil.baemo.domains.clubs.application.ports.output.comment.QueryClubPostCommentOutputPort;
import hotil.baemo.domains.clubs.application.ports.output.post.CommandClubPostEventOutputPort;
import hotil.baemo.domains.clubs.application.ports.output.post.CommandClubPostOutputPort;
import hotil.baemo.domains.clubs.application.usecases.comment.command.CreateClubPostCommentUseCase;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostCommentId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.policy.comment.CreateClubPostCommentPolicy;
import hotil.baemo.domains.clubs.domain.value.comment.CommentContent;
import hotil.baemo.domains.clubs.domain.value.comment.CommentDepth;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateClubPostCommentInputPort implements CreateClubPostCommentUseCase {
    private final QueryClubPostCommentOutputPort queryClubPostCommentOutputPort;
    private final CommandClubPostCommentOutPort commandClubPostCommentOutPort;
    private final CommandClubPostOutputPort commandClubPostOutputPort;
    private final CommandClubPostEventOutputPort commandClubPostEventOutputPort;

    @Override
    public QClubPostCommentDTO.Create create(ClubPostId postId, UserId writerId, ClubPostCommentId preCommentId, CommentDepth commentDepth, CommentContent commentContent) {
        return CreateClubPostCommentPolicy.execute(postId, writerId)
            .validRole(queryClubPostCommentOutputPort::loadClubsUser)
            .load(commandClubPostOutputPort::loadClubPost)
            .create(preCommentId, commentContent, commentDepth)
            .save(commandClubPostCommentOutPort::save)
            .produce(commandClubPostEventOutputPort::sendCommentedEvent);
    }
}