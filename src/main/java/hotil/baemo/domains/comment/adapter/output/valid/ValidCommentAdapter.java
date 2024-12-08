package hotil.baemo.domains.comment.adapter.output.valid;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.comment.adapter.output.persistence.repository.CommentJpaRepository;
import hotil.baemo.domains.comment.application.ports.output.valid.ValidCommentOutputPort;
import hotil.baemo.domains.comment.domain.entity.CommentId;
import hotil.baemo.domains.comment.domain.entity.CommentWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hotil.baemo.core.util.BaeMoObjectUtil.isEquals;
import static hotil.baemo.core.util.BaeMoObjectUtil.isNotEquals;

@Service
@RequiredArgsConstructor
class ValidCommentAdapter implements ValidCommentOutputPort {
    private final CommentJpaRepository commentJpaRepository;

    @Override
    public void validAuthority(CommentWriter commentWriter, CommentId commentId) {
        final var enticommentEntity = commentJpaRepository.load(commentId);

        if (isNotEquals(enticommentEntity.getWriterId(), commentWriter.id())) {
            throw new CustomException(ResponseCode.COMMENT_AUTH_FAIL);
        }
    }

    @Override
    public void validNotAuthor(CommentWriter commentWriter, CommentId commentId) {
        final var enticommentEntity = commentJpaRepository.load(commentId);

        if (isEquals(enticommentEntity.getWriterId(), commentWriter.id())) {
            throw new CustomException(ResponseCode.COMMENT_SELF_LIKE_NOT_ALLOW);
        }
    }

    @Override
    public void validStatus(CommentId commentId) {
        final var commentEntity = commentJpaRepository.load(commentId);

        if (isEquals(commentEntity.getIsDelete(), true)) {
            throw new CustomException(ResponseCode.COMMENT_NOT_FOUND);
        }
    }
}