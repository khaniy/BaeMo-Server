package hotil.baemo.domains.comment.adapter.output.persistence;

import hotil.baemo.domains.comment.adapter.output.persistence.entity.CommentEntity;
import hotil.baemo.domains.comment.adapter.output.persistence.repository.CommentJpaRepository;
import hotil.baemo.domains.comment.application.ports.output.CommandCommentOutputPort;
import hotil.baemo.domains.comment.domain.entity.CommentCommunityId;
import hotil.baemo.domains.comment.domain.entity.CommentId;
import hotil.baemo.domains.comment.domain.entity.CommentWriter;
import hotil.baemo.domains.comment.domain.entity.PreCommentId;
import hotil.baemo.domains.comment.domain.value.CommentContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommandCommentOutputAdapter implements CommandCommentOutputPort {
    private final CommentJpaRepository commentJpaRepository;

    @Override
    public CommentId save(CommentCommunityId communityId, PreCommentId preCommentId, CommentContent commentContent, CommentWriter writer) {
        final var saved = commentJpaRepository.save(CommentEntity.builder()
            .communityId(communityId.communityId())
            .preCommentId(preCommentId.id() == null ? null : preCommentId.id())
            .content(commentContent.content())
            .writerId(writer.id())
            .build());

        return new CommentId(saved.getCommentId());
    }

    @Override
    public void update(CommentId commentId, CommentContent newCommentContent, CommentWriter commentWriter) {
        final var commentEntity = commentJpaRepository.load(commentId);
        commentEntity.updateContent(newCommentContent.content());
    }

    @Override
    public void delete(CommentId commentId, CommentWriter commentWriter) {
        final var commentEntity = commentJpaRepository.load(commentId);
        commentEntity.delete();
    }
}