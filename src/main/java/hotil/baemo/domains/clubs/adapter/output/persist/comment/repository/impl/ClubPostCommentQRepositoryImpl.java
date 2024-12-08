package hotil.baemo.domains.clubs.adapter.output.persist.comment.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.clubs.adapter.output.persist.comment.dto.RepliesUserDTO;
import hotil.baemo.domains.clubs.adapter.output.persist.comment.entity.QClubPostCommentEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.comment.entity.QClubPostCommentLikeEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.comment.repository.ClubPostCommentQRepository;
import hotil.baemo.domains.clubs.adapter.output.persist.member.entity.QClubsMemberEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.post.entity.QClubsPostEntity;
import hotil.baemo.domains.clubs.application.dto.QClubPostCommentDTO;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.member.ClubRole;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubPostCommentQRepositoryImpl implements ClubPostCommentQRepository {
    private static final QClubPostCommentEntity COMMENT = QClubPostCommentEntity.clubPostCommentEntity;
    private static final QClubPostCommentLikeEntity COMMENT_LIKE = QClubPostCommentLikeEntity.clubPostCommentLikeEntity;
    private static final QClubPostCommentLikeEntity IS_LIKED_BY_USER = QClubPostCommentLikeEntity.clubPostCommentLikeEntity;
    private static final QClubsMemberEntity CLUBS_MEMBER = QClubsMemberEntity.clubsMemberEntity;
    private static final QClubsPostEntity POST = QClubsPostEntity.clubsPostEntity;
    private static final QUserEntity USER = QUserEntity.userEntity;
    private final JPAQueryFactory factory;

    @Override
    public ClubMember loadClubsUser(ClubPostId clubPostId, UserId userId) {
        final var clubId = factory
            .select(POST.clubsId)
            .from(POST)
            .where(POST.clubsPostId.eq(clubPostId.id()))
            .fetchFirst();

        final var result = factory
            .selectFrom(CLUBS_MEMBER)
            .where(CLUBS_MEMBER.usersId.eq(userId.id())
                .and(CLUBS_MEMBER.clubsId.eq(clubId))
                .and(CLUBS_MEMBER.isDelete.isFalse())
            )
            .fetchFirst();

        ClubRole role = (result != null) ? result.getClubRole() : ClubRole.NON_MEMBER;

        return ClubMember.builder()
            .clubId(new ClubId(clubId))
            .userId(userId)
            .role(role)
            .build();
    }

    @Override
    public RepliesUserDTO.SimpleInformationDTO loadUserSimpleInformation(final Long repliesWriter) {
        return factory.select(Projections.constructor(RepliesUserDTO.SimpleInformationDTO.class,
                USER.realName,
                USER.profileImage
            ))
            .from(USER)
            .where(USER.id.eq(repliesWriter))
            .fetchFirst();
    }

    @Override
    public QClubPostCommentDTO.CommentDetailList loadCommentList(UserId userId, ClubPostId postId) {
        final var result = factory
            .select(Projections.constructor(QClubPostCommentDTO.Comment.class,
                USER.id.as("writerId"),
                USER.realName.as("writerName"),
                USER.profileImage.as("writerThumbnail"),
                COMMENT.id.as("commentId"),
                COMMENT.depth.as("depth"),
                COMMENT.content.as("content"),
                COMMENT.preCommentId.as("preCommentId"),
                COMMENT_LIKE.count().as("likeCount"),
                COMMENT.createdAt.as("createdAt"),
                COMMENT.createdAt.as("updatedAt"),
                IS_LIKED_BY_USER.isLike.coalesce(false).as("isLikedByUser"))
            )
            .from(COMMENT)
            .join(USER).on(USER.id.eq(COMMENT.writerId))
            .leftJoin(COMMENT_LIKE).on(COMMENT_LIKE.commentId.eq(COMMENT.id))
            .leftJoin(IS_LIKED_BY_USER).on(
                IS_LIKED_BY_USER.commentId.eq(COMMENT.id),
                IS_LIKED_BY_USER.clubsUserId.eq(userId.id())
            )
            .where(COMMENT.clubPostId.eq(postId.id()))
            .groupBy(
                USER.id,
                USER.realName,
                USER.profileImage,
                COMMENT.id,
                COMMENT.content,
                COMMENT.preCommentId,
                IS_LIKED_BY_USER.isLike
            )
            .orderBy(COMMENT.createdAt.asc())
            .fetch();

        return QClubPostCommentDTO.CommentDetailList.builder()
            .list(result)
            .build();
    }
}