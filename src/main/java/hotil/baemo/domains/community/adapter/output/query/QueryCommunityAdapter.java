package hotil.baemo.domains.community.adapter.output.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.util.BaeMoQueryUtil;
import hotil.baemo.domains.comment.adapter.output.persistence.entity.QCommentEntity;
import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityImageEntity;
import hotil.baemo.domains.community.adapter.output.persistence.entity.QCommunityEntity;
import hotil.baemo.domains.community.adapter.output.persistence.entity.QCommunityImageEntity;
import hotil.baemo.domains.community.adapter.output.persistence.entity.QCommunityLikeEntity;
import hotil.baemo.domains.community.application.ports.output.query.QueryCommunityOutputPort;
import hotil.baemo.domains.community.application.value.query.RetrieveCommunity;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.CategoryList;
import hotil.baemo.domains.community.domain.value.image.CommunityImageList;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

import static com.querydsl.jpa.JPAExpressions.select;
import static hotil.baemo.core.util.BaeMoObjectUtil.isNullValue;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class QueryCommunityAdapter implements QueryCommunityOutputPort {
    private static final QCommunityEntity COMMUNITY = QCommunityEntity.communityEntity;
    private static final QCommentEntity COMMENT = QCommentEntity.commentEntity;
    private static final QCommunityImageEntity COMMUNITY_IMAGE = QCommunityImageEntity.communityImageEntity;

    private static final String COMMUNITY_IMAGE_LIST_ALIAS = "imageList";
    private static final QCommunityImageEntity COMMUNITY_IMAGE_LIST = new QCommunityImageEntity(COMMUNITY_IMAGE_LIST_ALIAS);

    private static final String COMMUNITY_LIKE_COUNT_ALIAS = "likeCount";
    private static final String COMMUNITY_LIKE_BY_USER_ALIAS = "likedByUser";
    private static final QCommunityLikeEntity COMMUNITY_LIKE_COUNT = new QCommunityLikeEntity(COMMUNITY_LIKE_COUNT_ALIAS);
    private static final QCommunityLikeEntity COMMUNITY_LIKE_BY_USER = new QCommunityLikeEntity(COMMUNITY_LIKE_BY_USER_ALIAS);

    private static final QUserEntity USER = QUserEntity.userEntity;

    private final JPAQueryFactory factory;
    private final BaeMoQueryUtil queryUtil;

    @Override
    public RetrieveCommunity.CommunityPreviewListDTO read(CommunityUserId communityUserId, Pageable pageable, CategoryList categoryList) {
        final var result = factory
            .select(Projections.constructor(RetrieveCommunity.CommunityPreview.class,
                    COMMUNITY.communityId,
                    COMMUNITY.communityCategory.as("category"),
                    COMMUNITY.title,
                    COMMUNITY.content,
                    COMMUNITY_IMAGE.image.as("thumbnail"),

                    communityLikeCount(),
                    COMMUNITY.viewCount,
                    commentCount(),

                    COMMUNITY.createdAt,
                    COMMUNITY.updatedAt,

                    USER.id.as("writerId"),
                    USER.nickname,
                    USER.realName,
                    USER.profileImage,
                    likedByUser(communityUserId)
                )
            )

            .from(COMMUNITY)
            .where(
                communityConditions(categoryList)
            )

            .leftJoin(USER)
            .on(
                userNotDeleteCondition(),
                userWriterCondition()
            )

            .leftJoin(COMMUNITY_IMAGE)
            .on(
                communityThumbnailCondition(),
                communityImageNotDeleteCondition(COMMUNITY_IMAGE),
                communityImageCommunityIdCondition()
            )

            .orderBy(queryUtil.createOrderSpecifier(pageable, createEntityPath()))
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())

            .fetch();

        return RetrieveCommunity.CommunityPreviewListDTO.builder()
            .list(result)
            .build();
    }

    @Override
    public RetrieveCommunity.CommunityDetails readDetails(CommunityId communityId, CommunityUserId communityUserId) {
        return factory
            .select(Projections.constructor(RetrieveCommunity.CommunityDetails.class,
                COMMUNITY.communityId,

                USER.id.as("writerId"),
                USER.profileImage,
                USER.nickname,

                COMMUNITY.communityCategory.as("category"),
                COMMUNITY.title,
                COMMUNITY.content,

                communityLikeCount(),
                COMMUNITY.viewCount,

                COMMUNITY.createdAt,
                COMMUNITY.updatedAt,
                likedByUser(communityUserId)
            ))

            .from(COMMUNITY)
            .where(
                communityIdCondition(communityId),
                communityNotDeleteCondition()
            )

            .leftJoin(USER)
            .on(
                userNotDeleteCondition(),
                userWriterCondition()
            )

            .fetchOne();
    }

    @Override
    public CommunityImageList loadImageList(CommunityId communityId) {
        final var result = factory
            .selectFrom(COMMUNITY_IMAGE_LIST)
            .where(
                communityImageNotDeleteCondition(COMMUNITY_IMAGE_LIST),
                communityImageCommunityIdCondition(communityId)
            )
            .fetch()
            .stream()
            .map(CommunityImageEntity::toCommunityImageDetails)
            .toList();

        return CommunityImageList.builder()
            .list(result)
            .build();
    }

    private Predicate communityNotThumbnailCondition() {
        return COMMUNITY_IMAGE_LIST.isThumbnail.isFalse();
    }

    private BooleanExpression communityIdCondition(CommunityId communityId) {
        return COMMUNITY.communityId.eq(communityId.id());
    }

    private BooleanBuilder communityConditions(CategoryList categoryList) {
        final var communityConditions = new BooleanBuilder();

        communityNotDeleteCondition(communityConditions);
        categoryCondition(categoryList, communityConditions);

        return communityConditions;
    }

    private Function<String, Expression<? extends Comparable<?>>> createEntityPath() {
        return property -> {
            if (property.equals(COMMUNITY_LIKE_COUNT_ALIAS)) {
                return COMMUNITY_LIKE_COUNT.count();
            }

            return COMMUNITY.createdAt;
        };
    }

    private Expression<Long> commentCount() {
        return ExpressionUtils.as(
            select(COMMENT.commentId.countDistinct())
                .from(COMMENT)
                .where(
                    commentCommunityIdCondition()
                ), "commentCount"
        );
    }

    private Expression<Long> communityLikeCount() {
        return ExpressionUtils.as(
            select(COMMUNITY_LIKE_COUNT.id.countDistinct())
                .from(COMMUNITY_LIKE_COUNT)
                .where(
                    likeTrueCondition(COMMUNITY_LIKE_COUNT),
                    likeCommunityIdCondition(COMMUNITY_LIKE_COUNT)
                ), COMMUNITY_LIKE_COUNT_ALIAS);
    }

    private BooleanExpression likedByUser(CommunityUserId communityUserId) {
        return select()
            .from(COMMUNITY_LIKE_BY_USER)
            .where(
                likeTrueCondition(COMMUNITY_LIKE_BY_USER),
                likeUserCondition(communityUserId),
                likeCommunityIdCondition(COMMUNITY_LIKE_BY_USER)
            )
            .exists().as(COMMUNITY_LIKE_BY_USER_ALIAS);
    }

    private BooleanExpression commentCommunityIdCondition() {
        return COMMENT.communityId.eq(COMMUNITY.communityId);
    }

    private void communityNotDeleteCondition(BooleanBuilder communityConditions) {
        communityConditions.and(COMMUNITY.isDelete.isFalse());
    }

    private BooleanExpression communityNotDeleteCondition() {
        return COMMUNITY.isDelete.isFalse();
    }

    private void categoryCondition(CategoryList categoryList, BooleanBuilder communityConditions) {
        if (isNullValue(categoryList)) {
            return;
        }

        final var orCondition = new BooleanBuilder();
        categoryList.forEach(e -> orCondition.or(COMMUNITY.communityCategory.eq(e)));
        communityConditions.and(orCondition);
    }

    private BooleanExpression communityThumbnailCondition() {
        return COMMUNITY_IMAGE.isThumbnail.isTrue();
    }

    private BooleanExpression communityImageNotDeleteCondition(QCommunityImageEntity qCommunityImageEntity) {
        return qCommunityImageEntity.isDelete.isFalse();
    }

    private BooleanExpression communityImageCommunityIdCondition() {
        return COMMUNITY_IMAGE.communityId.eq(COMMUNITY.communityId);
    }

    private BooleanExpression communityImageCommunityIdCondition(CommunityId communityId) {
        return COMMUNITY_IMAGE_LIST.communityId.eq(communityId.id());
    }

    private BooleanExpression userNotDeleteCondition() {
        return USER.isDel.isFalse();
    }

    private BooleanExpression userWriterCondition() {
        return USER.id.eq(COMMUNITY.writer);
    }

    private BooleanExpression likeTrueCondition(QCommunityLikeEntity likeEntity) {
        return likeEntity.isLike.isTrue();
    }

    private BooleanExpression likeUserCondition(final CommunityUserId communityUserId) {
        return COMMUNITY_LIKE_BY_USER.userId.eq(communityUserId.id());
    }

    private BooleanExpression likeCommunityIdCondition(QCommunityLikeEntity likeEntity) {
        return likeEntity.communityId.eq(COMMUNITY.communityId);
    }
}