package hotil.baemo.domains.clubs.adapter.post.output.persistence.dsl.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.util.BaeMoTimeUtil;
import hotil.baemo.domains.clubs.adapter.post.output.persistence.dsl.QueryClubsPostDslRepository;
import hotil.baemo.domains.clubs.adapter.post.output.persistence.entity.QClubsPostEntity;
import hotil.baemo.domains.clubs.adapter.post.output.persistence.entity.QClubsPostImageEntity;
import hotil.baemo.domains.clubs.adapter.post.output.persistence.entity.QClubsPostLikeEntity;
import hotil.baemo.domains.clubs.adapter.replies.output.persistence.entity.QRepliesEntity;
import hotil.baemo.domains.clubs.adapter.replies.output.persistence.entity.QStatRepliesEntity;
import hotil.baemo.domains.clubs.application.post.usecases.query.dto.DetailsClubsPostDTO;
import hotil.baemo.domains.clubs.application.post.usecases.query.dto.RetrieveClubsPostDTO;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostType;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QAbstractBaeMoUsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QQueryClubsPostAdapterImpl implements QueryClubsPostDslRepository {
    private static final QClubsPostEntity CLUBS_POST = QClubsPostEntity.clubsPostEntity;
    private static final QClubsPostImageEntity CLUBS_POST_IMAGE = QClubsPostImageEntity.clubsPostImageEntity;
    private static final QClubsPostLikeEntity CLUBS_POST_LIKE = QClubsPostLikeEntity.clubsPostLikeEntity;
    private static final QRepliesEntity REPLIES = QRepliesEntity.repliesEntity;
    private static final QAbstractBaeMoUsersEntity USER = QAbstractBaeMoUsersEntity.abstractBaeMoUsersEntity;
    private static final QStatRepliesEntity STAT_REPLIES = QStatRepliesEntity.statRepliesEntity;
    private final JPAQueryFactory factory;

    @Override
    public List<RetrieveClubsPostDTO.PreviewNoticeDTO> loadPreviewNoticeDTOList(ClubsId clubsId) {
        return factory
            .select(Projections.constructor(RetrieveClubsPostDTO.PreviewNoticeDTO.class,
                CLUBS_POST.clubsPostId.as("clubsPostId"),
                CLUBS_POST.clubsPostTitle.as("title"),
                Expressions.stringTemplate("substring({0}, 1, 100)", CLUBS_POST.clubsPostContent).as("content"),
                CLUBS_POST.clubsPostType.as("type"),
                CLUBS_POST.createdAt.as("createdAt"),
                CLUBS_POST.viewCount.as("viewCount"),

                CLUBS_POST_LIKE.count().as("likeCount"),
                REPLIES.count().as("repliesCount")
            ))
            .from(CLUBS_POST)

            .leftJoin(REPLIES).on(REPLIES.repliesPostId.eq(CLUBS_POST.clubsPostId))
            .leftJoin(CLUBS_POST_LIKE).on(CLUBS_POST_LIKE.clubsPostId.eq(CLUBS_POST.clubsPostId))

            .where(CLUBS_POST.clubsId.eq(clubsId.clubsId())
                .and(CLUBS_POST.clubsPostType.eq(ClubsPostType.NOTICE))
                .and(CLUBS_POST.isDelete.isFalse()))

            .groupBy(CLUBS_POST.clubsPostId)

            .orderBy(CLUBS_POST.createdAt.desc())
            .limit(5)
            .fetch();
    }

    @Override
    public List<RetrieveClubsPostDTO.PreviewClubsPostDTO> loadPreviewClubsPostDTOList(ClubsUserId clubsUserId, ClubsId clubsId, Pageable pageable) {
        final var thumbnailPath = new QClubsPostImageEntity("thumbnailPath");
        final var isLikedByUser = new QClubsPostLikeEntity("isLikedByUser");

        return factory
            .select(Projections.constructor(RetrieveClubsPostDTO.PreviewClubsPostDTO.class,
                USER.id.as("writerId"),
                USER.realName.as("nickname"),
                USER.profileImage.as("profileImage"),

                CLUBS_POST.clubsPostId.as("clubsPostId"),
                CLUBS_POST.clubsPostTitle.as("title"),
                Expressions.stringTemplate("substring({0}, 1, 100)", CLUBS_POST.clubsPostContent).as("content"),
                CLUBS_POST.clubsPostType.as("type"),
                thumbnailPath.imagePath.as("thumbnailPath"),

                CLUBS_POST.createdAt.as("createdAt"),

                CLUBS_POST_LIKE.count().as("likeCount"),
                REPLIES.count().as("repliesCount"),
                CLUBS_POST.viewCount.as("viewCount"),
                ExpressionUtils.as(JPAExpressions
                    .select(Expressions.booleanTemplate("coalesce({0}, false)", isLikedByUser.isLike))
                    .from(isLikedByUser)
                    .where(isLikedByUser.clubsUserId.eq(clubsUserId.id())
                        .and(isLikedByUser.clubsPostId.eq(CLUBS_POST.clubsPostId))), "isLikedByUser"))
            )
            .from(CLUBS_POST)

            .leftJoin(USER)
            .on(USER.id.eq(CLUBS_POST.clubsPostWriter))

            .leftJoin(thumbnailPath)
            .on(thumbnailPath.clubsPostId.eq(CLUBS_POST.clubsPostId)
                .and(thumbnailPath.clubsPostImageId.eq(
                    JPAExpressions.select(thumbnailPath.clubsPostImageId.min())
                        .from(thumbnailPath)
                        .where(thumbnailPath.clubsPostId.eq(CLUBS_POST.clubsPostId)
                            .and(thumbnailPath.isThumbnail.isTrue())
                            .and(thumbnailPath.isDeleted.isFalse())
                        )
                ))
            )

            .leftJoin(CLUBS_POST_LIKE)
            .on(CLUBS_POST_LIKE.clubsPostId.eq(CLUBS_POST.clubsPostId))

            .leftJoin(REPLIES)
            .on(REPLIES.repliesPostId.eq(CLUBS_POST.clubsPostId))

            .where(CLUBS_POST.clubsId.eq(clubsId.clubsId())
                .and(CLUBS_POST.isDelete.isFalse()))

            .groupBy(
                USER.id,
                CLUBS_POST.clubsPostId,
                thumbnailPath.imagePath
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(CLUBS_POST.createdAt.desc())
            .fetch();
    }

    @Override
    public List<RetrieveClubsPostDTO.PreviewClubsPostDTO> loadFilteredPreviewClubsPostDTOList(ClubsUserId clubsUserId, ClubsId clubsId, ClubsPostType type, Pageable pageable) {
        final var thumbnailPath = new QClubsPostImageEntity("thumbnailPath");
        final var isLikedByUser = new QClubsPostLikeEntity("isLikedByUser");
        return factory
            .select(Projections.constructor(RetrieveClubsPostDTO.PreviewClubsPostDTO.class,
                USER.id.as("writerId"),
                USER.realName.as("nickname"),
                USER.profileImage.as("profileImage"),

                CLUBS_POST.clubsPostId.as("clubsPostId"),
                CLUBS_POST.clubsPostTitle.as("title"),
                Expressions.stringTemplate("substring({0}, 1, 100)", CLUBS_POST.clubsPostContent).as("content"),
                CLUBS_POST.clubsPostType.as("type"),
                thumbnailPath.imagePath.as("thumbnailPath"),

                CLUBS_POST.createdAt.as("createdAt"),

                CLUBS_POST_LIKE.count().as("likeCount"),
                REPLIES.count().as("repliesCount"),
                CLUBS_POST.viewCount.as("viewCount"),
                ExpressionUtils.as(JPAExpressions
                    .select(Expressions.booleanTemplate("coalesce({0}, false)", isLikedByUser.isLike))
                    .from(isLikedByUser)
                    .where(isLikedByUser.clubsUserId.eq(clubsUserId.id())
                        .and(isLikedByUser.clubsPostId.eq(CLUBS_POST.clubsPostId))), "isLikedByUser"))
            )
            .from(CLUBS_POST)

            .leftJoin(USER)
            .on(USER.id.eq(CLUBS_POST.clubsPostWriter))

            .leftJoin(thumbnailPath)
            .on(thumbnailPath.clubsPostId.eq(CLUBS_POST.clubsPostId)
                .and(thumbnailPath.clubsPostImageId.eq(
                    JPAExpressions.select(thumbnailPath.clubsPostImageId.min())
                        .from(thumbnailPath)
                        .where(thumbnailPath.clubsPostId.eq(CLUBS_POST.clubsPostId)
                            .and(thumbnailPath.isThumbnail.isTrue())
                            .and(thumbnailPath.isDeleted.isFalse())
                        )
                ))
            )

            .leftJoin(CLUBS_POST_LIKE)
            .on(CLUBS_POST_LIKE.clubsPostId.eq(CLUBS_POST.clubsPostId))

            .leftJoin(REPLIES)
            .on(REPLIES.repliesPostId.eq(CLUBS_POST.clubsPostId))

            .where(CLUBS_POST.clubsId.eq(clubsId.clubsId())
                    .and(CLUBS_POST.clubsPostType.eq(type))
                    .and(CLUBS_POST.isDelete.isFalse()),
                postDeleteCondition()
            )

            .groupBy(
                USER.id,
                CLUBS_POST.clubsPostId,
                thumbnailPath.imagePath
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(CLUBS_POST.updatedAt.desc())
            .fetch();
    }

    @Override
    public DetailsClubsPostDTO.WriterDTO loadWriter(ClubsPostId clubsPostId) {
        final var userId = factory.select(CLUBS_POST.clubsPostWriter)
            .from(CLUBS_POST)
            .where(postIdCondition(clubsPostId))
            .fetchOne();

        return factory
            .select(Projections.constructor(DetailsClubsPostDTO.WriterDTO.class,
                USER.id.as("writerId"),
                USER.realName.as("writerName"),
                USER.profileImage.as("writerThumbnail"))
            )
            .from(USER)
            .where(USER.id.eq(userId))
            .fetchOne();
    }

    @Override
    public DetailsClubsPostDTO.PostDTO loadPost(ClubsPostId clubsPostId) {
        final var imageList = factory
            .select(Projections.constructor(DetailsClubsPostDTO.PostImageDetails.class,
                CLUBS_POST_IMAGE.imagePath.as("path"),
                CLUBS_POST_IMAGE.orderNumber.as("orderNumber"),
                CLUBS_POST_IMAGE.isThumbnail.as("isThumbnail")))
            .from(CLUBS_POST_IMAGE)

            .where(
                postImagePostIdCondition(clubsPostId),
                postImageDeleteCondition()
            )
            .fetch();

        final var postImageList = DetailsClubsPostDTO.PostImageList.builder()
            .postImageList(imageList)
            .build();

        final var tuple = factory
            .select(
                CLUBS_POST.clubsPostTitle,
                CLUBS_POST.clubsPostType,
                CLUBS_POST.clubsPostContent,

                CLUBS_POST.viewCount,
                CLUBS_POST.createdAt,
                CLUBS_POST.updatedAt
            )
            .from(CLUBS_POST)
            .where(
                postIdCondition(clubsPostId),
                postDeleteCondition()
            )
            .fetchOne();

        final var likeCount = factory
            .select(CLUBS_POST_LIKE.count())
            .from(CLUBS_POST_LIKE)
            .where(
                postLikeCondition(),
                postLikePostIdCondition(clubsPostId)
            )
            .fetchOne();

        assert tuple != null;

        return DetailsClubsPostDTO.PostDTO.builder()
            .title(tuple.get(CLUBS_POST.clubsPostTitle))
            .type(tuple.get(CLUBS_POST.clubsPostType))
            .content(tuple.get(CLUBS_POST.clubsPostContent))
            .viewCount(tuple.get(CLUBS_POST.viewCount))
            .createdAt(BaeMoTimeUtil.convert(Objects.requireNonNull(tuple.get(CLUBS_POST.createdAt))))
            .updatedAt(BaeMoTimeUtil.convert(Objects.requireNonNull(tuple.get(CLUBS_POST.updatedAt))))
            .likeCount(likeCount)
            .postImageList(postImageList)
            .build();
    }

    @Override
    public DetailsClubsPostDTO.RepliesList loadRepliesList(ClubsPostId clubsPostId) {
        final var fetch = factory.select(
                REPLIES.repliesId,
                REPLIES.repliesWriter,
                REPLIES.repliesContent,
                REPLIES.preRepliesId,
                STAT_REPLIES.count().as("likeCount")
            )
            .from(REPLIES)
            .leftJoin(STAT_REPLIES).on(STAT_REPLIES.repliesId.eq(REPLIES.repliesId).and(STAT_REPLIES.isLike.isTrue()))
            .where(REPLIES.repliesPostId.eq(clubsPostId.id()))
            .groupBy(REPLIES.repliesId, REPLIES.repliesWriter, REPLIES.repliesContent, REPLIES.preRepliesId)
            .fetch();

        final List<DetailsClubsPostDTO.RepliesDTO> list = new ArrayList<>();

        for (Tuple tuple : fetch) {
            final var repliesId = tuple.get(REPLIES.repliesId);
            final var writerId = tuple.get(REPLIES.repliesWriter);
            final var content = tuple.get(REPLIES.repliesContent);
            final var preReplies = tuple.get(REPLIES.preRepliesId);
            final var likeCount = tuple.get(STAT_REPLIES.count().as("likeCount"));

            final var writerDTO = factory
                .select(Projections.constructor(DetailsClubsPostDTO.WriterDTO.class,
                    USER.id.as("writerId"),
                    USER.realName.as("writerName"),
                    USER.profileImage.as("writerThumbnail"))
                )
                .from(USER)
                .where(USER.id.eq(writerId))
                .fetchOne();

            list.add(DetailsClubsPostDTO.RepliesDTO.builder()
                .writerDTO(writerDTO)
                .repliesId(repliesId)
                .content(content)
                .preReplies(preReplies)
                .likeCount(likeCount)
                .build()
            );
        }

        return DetailsClubsPostDTO.RepliesList.builder()
            .repliesDTOList(list)
            .build();
    }

    private BooleanExpression postIdCondition(ClubsPostId clubsPostId) {
        return CLUBS_POST.clubsPostId.eq(clubsPostId.id());
    }

    private BooleanExpression postDeleteCondition() {
        return CLUBS_POST.isDelete.isFalse();
    }

    private BooleanExpression postImagePostIdCondition(ClubsPostId clubsPostId) {
        return CLUBS_POST_IMAGE.clubsPostId.eq(clubsPostId.id());
    }

    private BooleanExpression postImageDeleteCondition() {
        return CLUBS_POST_IMAGE.isDeleted.isFalse();
    }

    private BooleanExpression postLikePostIdCondition(ClubsPostId clubsPostId) {
        return CLUBS_POST_LIKE.clubsPostId.eq(clubsPostId.id());
    }

    private BooleanExpression postLikeCondition() {
        return CLUBS_POST_LIKE.isLike.isTrue();
    }
}