package hotil.baemo.domains.clubs.adapter.output.persist.post.repository.impl;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.output.persist.comment.entity.QClubPostCommentEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.post.entity.QClubsPostEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.post.entity.QClubsPostImageEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.post.entity.QClubsPostLikeEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.post.repository.ClubPostQRepository;
import hotil.baemo.domains.clubs.application.dto.QClubPostDTO;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostType;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QClubPostRepositoryImpl implements ClubPostQRepository {
    private static final QClubsPostEntity CLUBS_POST = QClubsPostEntity.clubsPostEntity;
    private static final QClubsPostImageEntity CLUBS_POST_IMAGE = QClubsPostImageEntity.clubsPostImageEntity;
    private static final QClubsPostLikeEntity CLUBS_POST_LIKE = QClubsPostLikeEntity.clubsPostLikeEntity;
    private static final QClubsPostLikeEntity IS_LIKED_BY_USER = QClubsPostLikeEntity.clubsPostLikeEntity;
    private static final QClubPostCommentEntity CLUB_POST_COMMENT = QClubPostCommentEntity.clubPostCommentEntity;
    private static final QUserEntity USER = QUserEntity.userEntity;
    private static final QUserEntity IS_AUTHOR = QUserEntity.userEntity;
    private final JPAQueryFactory factory;

    @Override
    public List<QClubPostDTO.ClubNoticeListView> loadPreviewNoticeDTOList(ClubId clubId, UserId userId) {
        return factory.select(constructClubNoticeListView())
            .from(CLUBS_POST)
            .where(CLUBS_POST.clubsId.eq(clubId.clubsId())
                .and(CLUBS_POST.clubsPostType.eq(ClubPostType.NOTICE))
                .and(CLUBS_POST.isDelete.isFalse()))
            .leftJoin(CLUB_POST_COMMENT).on(CLUB_POST_COMMENT.clubPostId.eq(CLUBS_POST.clubsPostId))
            .leftJoin(CLUBS_POST_LIKE).on(CLUBS_POST_LIKE.clubsPostId.eq(CLUBS_POST.clubsPostId))
            .leftJoin(IS_LIKED_BY_USER).on(IS_LIKED_BY_USER.clubsPostId.eq(CLUBS_POST.clubsPostId), IS_LIKED_BY_USER.clubsUserId.eq(userId.id()))
            .groupBy(CLUBS_POST.clubsPostId, IS_LIKED_BY_USER.isLike)
            .orderBy(CLUBS_POST.createdAt.desc())
            .limit(5)
            .fetch();
    }

    @Override
    public List<QClubPostDTO.ClubPostListView> loadPreviewClubsPostDTOList(UserId userId, ClubId clubId, Pageable pageable) {
        return factory
            .select(constructClubPostListView())
            .from(CLUBS_POST)
            .where(CLUBS_POST.clubsId.eq(clubId.clubsId())
                .and(CLUBS_POST.isDelete.isFalse()))
            .leftJoin(CLUBS_POST_LIKE).on(CLUBS_POST_LIKE.clubsPostId.eq(CLUBS_POST.clubsPostId))
            .leftJoin(IS_LIKED_BY_USER).on(IS_LIKED_BY_USER.clubsPostId.eq(CLUBS_POST.clubsPostId), IS_LIKED_BY_USER.clubsUserId.eq(userId.id()))
            .leftJoin(CLUB_POST_COMMENT).on(CLUB_POST_COMMENT.clubPostId.eq(CLUBS_POST.clubsPostId))
            .leftJoin(USER).on(USER.id.eq(CLUBS_POST.clubsPostWriter))
            .leftJoin(CLUBS_POST_IMAGE).on(
                CLUBS_POST_IMAGE.clubsPostId.eq(CLUBS_POST.clubsPostId),
                CLUBS_POST_IMAGE.isThumbnail.isTrue(),
                CLUBS_POST_IMAGE.isDeleted.isFalse()
            )
            .groupBy(USER.id, CLUBS_POST.clubsPostId, CLUBS_POST_IMAGE.imagePath, IS_LIKED_BY_USER.isLike)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(CLUBS_POST.createdAt.desc())
            .fetch();
    }

    @Override
    public List<QClubPostDTO.ClubPostListView> loadFilteredPreviewClubsPostDTOList(UserId userId, ClubId clubId, ClubPostType type, Pageable pageable) {
        return factory
            .select(constructClubPostListView())
            .from(CLUBS_POST)
            .where(CLUBS_POST.clubsId.eq(clubId.clubsId())
                .and(CLUBS_POST.clubsPostType.eq(type))
                .and(CLUBS_POST.isDelete.isFalse()))
            .leftJoin(CLUBS_POST_LIKE).on(CLUBS_POST_LIKE.clubsPostId.eq(CLUBS_POST.clubsPostId))
            .leftJoin(IS_LIKED_BY_USER).on(
                IS_LIKED_BY_USER.clubsPostId.eq(CLUBS_POST.clubsPostId),
                IS_LIKED_BY_USER.clubsUserId.eq(userId.id())
            )
            .leftJoin(CLUB_POST_COMMENT).on(CLUB_POST_COMMENT.clubPostId.eq(CLUBS_POST.clubsPostId))
            .leftJoin(USER).on(USER.id.eq(CLUBS_POST.clubsPostWriter))
            .leftJoin(CLUBS_POST_IMAGE).on(
                CLUBS_POST_IMAGE.clubsPostId.eq(CLUBS_POST.clubsPostId),
                CLUBS_POST_IMAGE.isThumbnail.isTrue(),
                CLUBS_POST_IMAGE.isDeleted.isFalse()
            )
            .groupBy(USER.id, CLUBS_POST.clubsPostId, CLUBS_POST_IMAGE.imagePath, IS_LIKED_BY_USER.isLike)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(CLUBS_POST.createdAt.desc())
            .fetch();
    }

    @Override
    public QClubPostDTO.ClubPostDetailView loadPost(ClubPostId clubPostId, UserId userId) {
        var imageList = factory
            .select(Projections.constructor(QClubPostDTO.PostImage.class,
                CLUBS_POST_IMAGE.imagePath,
                CLUBS_POST_IMAGE.orderNumber,
                CLUBS_POST_IMAGE.isThumbnail))
            .from(CLUBS_POST_IMAGE)
            .where(CLUBS_POST_IMAGE.clubsPostId.eq(clubPostId.id()))
            .fetch();
        final var result = factory.select(constructClubPostDetail(imageList))
            .from(CLUBS_POST)
            .leftJoin(USER).on(USER.id.eq(CLUBS_POST.clubsPostWriter))
            .leftJoin(CLUBS_POST_LIKE).on(CLUBS_POST_LIKE.clubsPostId.eq(CLUBS_POST.clubsPostId))
            .leftJoin(IS_LIKED_BY_USER).on(
                IS_LIKED_BY_USER.clubsPostId.eq(CLUBS_POST.clubsPostId),
                IS_LIKED_BY_USER.clubsUserId.eq(userId.id()))
            .leftJoin(IS_AUTHOR).on(
                IS_AUTHOR.id.eq(CLUBS_POST.clubsPostWriter),
                IS_AUTHOR.id.eq(userId.id()))
            .where(postIdCondition(clubPostId), postDeleteCondition())
            .groupBy(USER.id, CLUBS_POST.clubsPostId, IS_LIKED_BY_USER.isLike, IS_AUTHOR.id)
            .fetchFirst();
        if (result == null) {
            throw new CustomException(ResponseCode.CLUBS_POST_NOT_FOUND);
        }
        return result;
    }

    private BooleanExpression postIdCondition(ClubPostId clubPostId) {
        return CLUBS_POST.clubsPostId.eq(clubPostId.id());
    }

    private BooleanExpression postDeleteCondition() {
        return CLUBS_POST.isDelete.isFalse();
    }

    private BooleanExpression postImagePostIdCondition(ClubPostId clubPostId) {
        return CLUBS_POST_IMAGE.clubsPostId.eq(clubPostId.id());
    }

    private BooleanExpression postImageDeleteCondition() {
        return CLUBS_POST_IMAGE.isDeleted.isFalse();
    }

    private BooleanExpression postLikePostIdCondition(ClubPostId clubPostId) {
        return CLUBS_POST_LIKE.clubsPostId.eq(clubPostId.id());
    }

    private BooleanExpression postLikeCondition() {
        return CLUBS_POST_LIKE.isLike.isTrue();
    }

    private ConstructorExpression<QClubPostDTO.ClubNoticeListView> constructClubNoticeListView() {
        return Projections.constructor(QClubPostDTO.ClubNoticeListView.class,
            CLUBS_POST.clubsPostId.as("clubsPostId"),
            CLUBS_POST.clubsPostTitle.as("title"),
            Expressions.stringTemplate("substring({0}, 1, 100)", CLUBS_POST.clubsPostContent).as("content"),
            CLUBS_POST.clubsPostType.as("type"),
            CLUBS_POST.createdAt.as("createdAt"),
            CLUBS_POST.updatedAt.as("updatedAt"),
            CLUBS_POST.viewCount.as("viewCount"),
            CLUBS_POST_LIKE.count().as("likeCount"),
            CLUB_POST_COMMENT.count().as("repliesCount"),
            IS_LIKED_BY_USER.isLike.coalesce(false).as("isLikedByUser")
        );
    }


    private static ConstructorExpression<QClubPostDTO.ClubPostListView> constructClubPostListView() {
        return Projections.constructor(QClubPostDTO.ClubPostListView.class,
            USER.id.as("writerId"),
            USER.realName.as("nickname"),
            USER.profileImage.as("profileImage"),
            CLUBS_POST.clubsPostId.as("clubsPostId"),
            CLUBS_POST.clubsPostTitle.as("title"),
            Expressions.stringTemplate("substring({0}, 1, 100)", CLUBS_POST.clubsPostContent).as("content"),
            CLUBS_POST.clubsPostType.as("type"),
            CLUBS_POST_IMAGE.imagePath.as("thumbnailPath"),
            CLUBS_POST.createdAt.as("createdAt"),
            CLUBS_POST.updatedAt.as("updatedAt"),
            CLUBS_POST_LIKE.count().as("likeCount"),
            CLUB_POST_COMMENT.count().as("repliesCount"),
            CLUBS_POST.viewCount.as("viewCount"),
            IS_LIKED_BY_USER.isLike.coalesce(false).as("isLikedByUser")
        );
    }

    private static ConstructorExpression<QClubPostDTO.ClubPostDetailView> constructClubPostDetail(List<QClubPostDTO.PostImage> imageList) {
        return Projections.constructor(QClubPostDTO.ClubPostDetailView.class,
            USER.id,
            USER.realName,
            USER.profileImage,
            CLUBS_POST.clubsPostType,
            CLUBS_POST.clubsPostTitle,
            CLUBS_POST.clubsPostContent,
            CLUBS_POST.viewCount,
            CLUBS_POST.createdAt,
            CLUBS_POST.updatedAt,
            Expressions.constant(imageList),
            CLUBS_POST_LIKE.count(),
            IS_AUTHOR.id.isNotNull().coalesce(false),
            IS_LIKED_BY_USER.isLike.coalesce(false));
    }
}