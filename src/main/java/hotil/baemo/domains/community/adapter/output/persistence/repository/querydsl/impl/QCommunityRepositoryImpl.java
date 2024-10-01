package hotil.baemo.domains.community.adapter.output.persistence.repository.querydsl.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.core.util.BaeMoTimeUtil;
import hotil.baemo.domains.comment.adapter.output.persistence.entity.QCommentEntity;
import hotil.baemo.domains.community.adapter.output.persistence.entity.QCommunityEntity;
import hotil.baemo.domains.community.adapter.output.persistence.entity.QCommunityImageEntity;
import hotil.baemo.domains.community.adapter.output.persistence.repository.querydsl.QCommunityRepository;
import hotil.baemo.domains.community.application.dto.CommunityPreview;
import hotil.baemo.domains.community.application.dto.RetrieveCommunity;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.value.CategoryList;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QUsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QCommunityRepositoryImpl implements QCommunityRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommunityPreview> loadCommunityPreview(CategoryList category) {
        final var community = QCommunityEntity.communityEntity;
        final var communityImage = QCommunityImageEntity.communityImageEntity;
        final var comment = QCommentEntity.commentEntity;
        final var subCommunityImage = new QCommunityImageEntity("subCommunityImage");

        return queryFactory
            .select(
                community.communityId,
                community.communityCategory,

                community.title,
                community.content,
//                community.thumbnail,

                community.viewCount,
                community.likeCount,
                comment.communityId.count().as("commentCount")
            )
            .from(community)
            .leftJoin(communityImage)
            .on(communityImage.communityId.eq(community.communityId)
                .and(communityImage.communityImageId.eq(
                    JPAExpressions.select(communityImage.communityImageId.min())
                        .from(subCommunityImage)
                        .where(subCommunityImage.communityId.eq(community.communityId))
                ))
            )
            .leftJoin(comment).on(comment.communityId.eq(community.communityId))
            .where(community.communityCategory.in(category.stream().toList()))
            .groupBy(
                community.communityId,
                community.communityCategory,
                community.title,
                community.content,
//                community.,
                community.viewCount,
                community.likeCount
            )
            .orderBy(community.updatedAt.desc())
            .fetch()
            .stream()
            .map(tuple -> CommunityPreview.builder()
                .communityId(tuple.get(community.communityId))

                .category(String.valueOf(tuple.get(community.communityCategory)))
                .title(tuple.get(community.title))
                .content(tuple.get(community.content))
//                .thumbnail(tuple.get(community.thumbnail))

                .likeCount(tuple.get(community.likeCount))
                .viewCount(tuple.get(community.viewCount))
                .commentCount(tuple.get(comment.communityId.count().longValue()))

                .build()).toList();
    }

    @Override
    public RetrieveCommunity.CommunityDetails loadCommunityDetails(CommunityId communityId) {
        final var communityIdx = communityId.id();
        final var user = QUsersEntity.usersEntity;
        final var builder = getCommunityDetailsBuilder(communityId, user);

        builder.imageList(getImageList(communityIdx));
        builder.commentDetailsList(getCommentDetails(communityIdx, user));

        return builder.build();
    }

    private RetrieveCommunity.CommunityDetails.CommunityDetailsBuilder getCommunityDetailsBuilder(CommunityId communityId, QUsersEntity user) {
        final var community = QCommunityEntity.communityEntity;
        RetrieveCommunity.CommunityDetails.CommunityDetailsBuilder builder = RetrieveCommunity.CommunityDetails.builder();
        final Tuple communityResult = queryFactory
            .select(
                community.communityCategory,
                community.title,
                community.content,
                community.updatedAt,
                community.createdAt,

                user.id,
                user.profileImage,
                user.nickname
            )
            .from(community)
            .leftJoin(user).on(community.writer.eq(user.id))
            .where(community.communityId.eq(communityId.id()))
            .fetchOne();

        if (communityResult != null) {
            return builder
//                .category(Objects.requireNonNull(communityResult.get(community.communityCategory)).getDes()) TODO :
                .title(communityResult.get(community.title))
                .content(communityResult.get(community.content))
                .createdAt(BaeMoTimeUtil.convert(Objects.requireNonNull(communityResult.get(community.createdAt))))
                .updatedAt(BaeMoTimeUtil.convert(Objects.requireNonNull(communityResult.get(community.updatedAt))))

                .writerId(communityResult.get(community.writer))
                .nickname(communityResult.get(user.nickname))
                .profileImage(communityResult.get(user.profileImage));
        }

        throw new CustomException(ResponseCode.QUERYDSL_ERROR);
    }

    private List<RetrieveCommunity.CommentDetails> getCommentDetails(Long communityIdx, QUsersEntity user) {
//        final var comment = QCommentEntity.commentEntity;
//        final var commentListResult = queryFactory
//            .select(
//                comment.writerId,
//                comment.content,
//                comment.preCommentId,
//                comment.likeCount,
//                comment.updatedAt,
//                comment.createdAt,
//                user.nickname,
//                user.profileImage
//            )
//            .from(comment)
//            .leftJoin(user).on(comment.writerId.eq(user.id))
//            .where(comment.communityId.eq(communityIdx))
//            .orderBy(comment.createdAt.asc())
//            .fetch();
//
//        return commentListResult.stream()
//            .map(e -> RetrieveCommunity.CommentDetails.builder()
//                .writerId(e.get(comment.writerId))
//                .commentId(communityIdx)
//                .content(e.get(comment.content))
//                .createdAt(BaeMoTimeUtil.convert(Objects.requireNonNull(e.get(comment.createdAt))))
//                .updatedAt(BaeMoTimeUtil.convert(Objects.requireNonNull(e.get(comment.updatedAt))))
//                .likeCount(e.get(comment.likeCount))
//                .nickname(e.get(user.nickname))
//                .profileImage(e.get(user.profileImage))
//                .build())
//            .toList();
        return null;
    }

    private List<String> getImageList(Long communityIdx) {
        final QCommunityImageEntity communityImage = QCommunityImageEntity.communityImageEntity;
        final var communityImageResult = queryFactory
            .select(
                communityImage.communityId,
                communityImage.image,
                communityImage.communityImageId
            )
            .from(communityImage)
            .where(communityImage.communityId.eq(communityIdx))
            .fetch();

        return communityImageResult.stream()
            .map(e -> e.get(communityImage.image))
            .toList();
    }
}