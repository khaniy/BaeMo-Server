package hotil.baemo.domains.comment.adapter.output.persistence;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.util.BaeMoQueryUtil;
import hotil.baemo.domains.comment.adapter.output.persistence.entity.QCommentEntity;
import hotil.baemo.domains.comment.adapter.output.persistence.entity.QCommentLikeEntity;
import hotil.baemo.domains.comment.application.dto.RetrieveComment;
import hotil.baemo.domains.comment.application.ports.output.QueryCommentOutputPort;
import hotil.baemo.domains.comment.domain.entity.CommentCommunityId;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QAbstractBaeMoUsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class QueryCommentOutputAdapter implements QueryCommentOutputPort {
    private static final QCommentEntity COMMENT = QCommentEntity.commentEntity;
    private static final QCommentLikeEntity LIKE = QCommentLikeEntity.commentLikeEntity;
    private static final QAbstractBaeMoUsersEntity USER = QAbstractBaeMoUsersEntity.abstractBaeMoUsersEntity;

    private final JPAQueryFactory factory;
    private final BaeMoQueryUtil queryUtil;

    @Override
    public RetrieveComment.CommentDetailsList retrieveCommentListByCommunity(CommentCommunityId communityId, Pageable pageable) {
        final var result = factory
            .select(
                Projections.constructor(RetrieveComment.CommentDetails.class,
                    COMMENT.commentId,
                    COMMENT.communityId,
                    COMMENT.preCommentId,
                    COMMENT.content,
                    LIKE.count().as("likeCount"),
                    COMMENT.isDelete,
                    COMMENT.createdAt,
                    COMMENT.updatedAt,
                    USER.id,
                    USER.nickname,
                    USER.realName,
                    USER.profileImage
                )
            )
            .from(COMMENT)
            .where(communityIdCondition(communityId))

            .leftJoin(USER)
            .on(
                userDeleteCondition(),
                userAuthorCondition(COMMENT.writerId)
            )

            .leftJoin(LIKE)
            .on(
                likeCondition(),
                likeCommentIdCondition(COMMENT.commentId)
            )

            .groupBy(
                COMMENT.commentId,
                COMMENT.communityId,
                COMMENT.preCommentId,
                COMMENT.content,
                COMMENT.isDelete,
                COMMENT.createdAt,
                COMMENT.updatedAt,
                USER.id,
                USER.nickname,
                USER.realName,
                USER.profileImage
            )

            .orderBy(queryUtil.createOrderSpecifier(pageable, createEntityPath()))
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())

            .fetch();

        return RetrieveComment.CommentDetailsList.builder()
            .list(result)
            .build();
    }

    private Function<String, Expression<? extends Comparable<?>>> defaultEntityPath() {
        return property -> COMMENT.createdAt;
    }

    private Function<String, Expression<? extends Comparable<?>>> createEntityPath() {
        return property -> {
            if (property.equals("likeCount")) {
                return LIKE.count();
            }

            return COMMENT.createdAt;
        };
    }

    private BooleanExpression communityIdCondition(CommentCommunityId commentCommunityId) {
        return COMMENT.communityId.eq(commentCommunityId.communityId());
    }

    private BooleanExpression userDeleteCondition() {
        return USER.isDel.isFalse();
    }

    private BooleanExpression userAuthorCondition(NumberPath<Long> commentWriter) {
        return USER.id.eq(commentWriter);
    }

    private BooleanExpression likeCommentIdCondition(NumberPath<Long> commentId) {
        return LIKE.commentId.eq(commentId);
    }

    private BooleanExpression likeCondition() {
        return LIKE.isLike.isTrue();
    }
}