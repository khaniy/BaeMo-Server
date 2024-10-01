package hotil.baemo.domains.comment.adapter.output.persistence;

import hotil.baemo.domains.comment.adapter.output.persistence.entity.CommentLikeEntity;
import hotil.baemo.domains.comment.adapter.output.persistence.repository.CommandLikeQueryRepository;
import hotil.baemo.domains.comment.adapter.output.persistence.repository.CommentLikeJpaRepository;
import hotil.baemo.domains.comment.application.ports.output.CommentLikeOutputPort;
import hotil.baemo.domains.comment.domain.entity.CommentId;
import hotil.baemo.domains.comment.domain.entity.CommentWriter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentLikeOutputAdapter implements CommentLikeOutputPort {
    private final CommentLikeJpaRepository commentLikeJpaRepository;
    private final CommandLikeQueryRepository commandLikeQueryRepository;

    @Override
    public void executeLikeToggle(CommentWriter user, CommentId commentId) {
        if (commandLikeQueryRepository.existsByUserIdAndCommentId(user, commentId)) {
            final var likeEntity = commentLikeJpaRepository.load(user, commentId);
            likeEntity.toggleLike();
            return;
        }

        commentLikeJpaRepository.save(CommentLikeEntity.builder()
            .userId(user.id())
            .commentId(commentId.id())
            .isLike(true)
            .build());
    }
}