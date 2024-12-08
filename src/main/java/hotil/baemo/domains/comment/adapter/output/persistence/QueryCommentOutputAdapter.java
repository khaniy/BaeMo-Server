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
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import static com.querydsl.jpa.JPAExpressions.select;

@Service
@RequiredArgsConstructor
public class QueryCommentOutputAdapter implements QueryCommentOutputPort {
    private static final QCommentEntity COMMENT = QCommentEntity.commentEntity;
    private static final QUserEntity USER = QUserEntity.userEntity;

    private static final String LIKE_COUNT_ALIAS = "likeCount";
    private static final String LIKE_BY_USER_ALIAS = "isLikedByUser";
    private static final QCommentLikeEntity LIKE_COUNT = new QCommentLikeEntity(LIKE_COUNT_ALIAS);
    private static final QCommentLikeEntity LIKE_BY_USER = new QCommentLikeEntity(LIKE_BY_USER_ALIAS);

    private final JPAQueryFactory factory;
    private final BaeMoQueryUtil queryUtil;

    @Override
    public RetrieveComment.CommentDetailsList retrieveCommentListByCommunity(CommentCommunityId communityId, CommunityUserId communityUserId, Pageable pageable) {
        final var result = factory
            .select(
                Projections.constructor(RetrieveComment.CommentDetails.class,
                    COMMENT.commentId,
                    COMMENT.communityId,
                    COMMENT.preCommentId,
                    COMMENT.content,
                    LIKE_COUNT.count().as(LIKE_COUNT_ALIAS),
                    COMMENT.isDelete,
                    COMMENT.createdAt,
                    COMMENT.updatedAt,

                    USER.id,
                    USER.nickname,
                    USER.realName,
                    USER.profileImage,
                    select(LIKE_BY_USER.isLike)
                        .from(LIKE_BY_USER)
                        .where(
                            likeTrueCondition(LIKE_BY_USER),
                            likeUserIdCondition(communityUserId),
                            likeCommentIdCondition(LIKE_BY_USER)
                        )
                        .exists().as(LIKE_BY_USER_ALIAS)
                )
            )
            .from(COMMENT)
            .where(communityIdCondition(communityId))

            .leftJoin(USER)
            .on(
                userNotDeletedCondition(),
                writerIdCondition(COMMENT.writerId)
            )

            .leftJoin(LIKE_COUNT)
            .on(
                likeTrueCondition(LIKE_COUNT),
                likeCommentIdCondition(LIKE_COUNT)
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

    private Function<String, Expression<? extends Comparable<?>>> createEntityPath() {
        return property -> {
            if (property.equals(LIKE_COUNT_ALIAS)) {
                return LIKE_COUNT.count();
            }

            return COMMENT.createdAt;
        };
    }

    private BooleanExpression communityIdCondition(CommentCommunityId commentCommunityId) {
        return COMMENT.communityId.eq(commentCommunityId.communityId());
    }

    private BooleanExpression userNotDeletedCondition() {
        return USER.isDel.isFalse();
    }

    private BooleanExpression writerIdCondition(NumberPath<Long> commentWriter) {
        return USER.id.eq(commentWriter);
    }

    private BooleanExpression likeCommentIdCondition(QCommentLikeEntity likeEntity) {
        return likeEntity.commentId.eq(COMMENT.commentId);
    }

    private BooleanExpression likeUserIdCondition(CommunityUserId userId) {
        return LIKE_BY_USER.userId.eq(userId.id());
    }

    private BooleanExpression likeTrueCondition(QCommentLikeEntity likeEntity) {
        return likeEntity.isLike.isTrue();
    }
}