package hotil.baemo.domains.clubs.application.ports.input.comment.query;

import hotil.baemo.domains.clubs.application.dto.QClubPostCommentDTO;
import hotil.baemo.domains.clubs.application.ports.output.comment.QueryClubPostCommentOutputPort;
import hotil.baemo.domains.clubs.application.usecases.comment.query.RetrieveClubPostCommentUseCase;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RetrieveClubPostCommentInputPort implements RetrieveClubPostCommentUseCase {
    private final QueryClubPostCommentOutputPort queryClubPostCommentOutputPort;

    @Override
    public QClubPostCommentDTO.CommentDetailList retrieve(UserId userId, ClubPostId postId) {
        return queryClubPostCommentOutputPort.loadRepliesDetailList(userId, postId);
    }
}