package hotil.baemo.domains.comment.adapter.output.persistence.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.comment.adapter.output.persistence.entity.QCommentLikeEntity;
import hotil.baemo.domains.comment.adapter.output.persistence.repository.CommandLikeQueryRepository;
import hotil.baemo.domains.comment.domain.entity.CommentId;
import hotil.baemo.domains.comment.domain.entity.CommentWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CommandLikeQueryRepositoryService implements CommandLikeQueryRepository {
    private static final QCommentLikeEntity LIKE = QCommentLikeEntity.commentLikeEntity;
    private final JPAQueryFactory factory;

    @Override
    public boolean existsByUserIdAndCommentId(CommentWriter user, CommentId commentId) {
        return factory
            .selectFrom(LIKE)
            .where(
                userCondition(user),
                commentIdCondition(commentId)
            )
            .fetchFirst() != null;
    }

    private BooleanExpression userCondition(CommentWriter user) {
        return LIKE.userId.eq(user.id());
    }

    private BooleanExpression commentIdCondition(CommentId commentId) {
        return LIKE.commentId.eq(commentId.id());
    }
}