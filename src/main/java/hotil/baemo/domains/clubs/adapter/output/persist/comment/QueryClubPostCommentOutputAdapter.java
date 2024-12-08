package hotil.baemo.domains.clubs.adapter.output.persist.comment;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.output.persist.comment.mapper.ClubPostCommentEntityMapper;
import hotil.baemo.domains.clubs.adapter.output.persist.comment.repository.ClubPostCommentJpaRepository;
import hotil.baemo.domains.clubs.adapter.output.persist.comment.repository.ClubPostCommentQRepository;
import hotil.baemo.domains.clubs.application.dto.QClubPostCommentDTO;
import hotil.baemo.domains.clubs.application.ports.output.comment.QueryClubPostCommentOutputPort;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostComment;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostCommentId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryClubPostCommentOutputAdapter implements QueryClubPostCommentOutputPort {
    private final ClubPostCommentQRepository clubPostCommentQRepository;
    private final ClubPostCommentJpaRepository clubPostCommentJpaRepository;

    @Override
    public ClubPostComment load(ClubPostCommentId clubPostCommentId) {
        return clubPostCommentJpaRepository.findById(clubPostCommentId.id())
            .map(ClubPostCommentEntityMapper::convert)
            .orElseThrow(() -> new CustomException(ResponseCode.REPLIES_NOT_FOUND));
    }

    @Override
    public QClubPostCommentDTO.CommentDetailList loadRepliesDetailList(UserId userId, ClubPostId clubPostId) {
        return clubPostCommentQRepository.loadCommentList(userId, clubPostId);
    }

    @Override
    public ClubMember loadClubsUser(ClubPostId clubPostId, UserId writerId) {
        return clubPostCommentQRepository.loadClubsUser(clubPostId, writerId);
    }
}