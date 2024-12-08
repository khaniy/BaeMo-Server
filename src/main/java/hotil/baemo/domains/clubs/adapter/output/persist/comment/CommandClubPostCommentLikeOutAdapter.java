package hotil.baemo.domains.clubs.adapter.output.persist.comment;

import hotil.baemo.domains.clubs.adapter.output.persist.comment.entity.ClubPostCommentLikeEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.comment.repository.ClubPostCommentLikeJpaRepository;
import hotil.baemo.domains.clubs.application.ports.output.comment.CommandClubPostCommentLikeOutPort;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.entity.comment.ClubPostCommentId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandClubPostCommentLikeOutAdapter implements CommandClubPostCommentLikeOutPort {
    private final ClubPostCommentLikeJpaRepository clubPostCommentLikeJpaRepository;

    @Override
    public void executeLike(ClubPostCommentId clubPostCommentId, UserId userId) {
        final var commentId = clubPostCommentId.id();
        final var clubsUserId = userId.id();

        clubPostCommentLikeJpaRepository.findByCommentIdAndClubsUserId(commentId, clubsUserId)
            .ifPresentOrElse(
                clubPostCommentLikeJpaRepository::delete,
                () -> clubPostCommentLikeJpaRepository.save(
                    ClubPostCommentLikeEntity.builder()
                        .isLike(true)
                        .commentId(commentId)
                        .clubsUserId(clubsUserId)
                        .build()
                )
            );
    }
}