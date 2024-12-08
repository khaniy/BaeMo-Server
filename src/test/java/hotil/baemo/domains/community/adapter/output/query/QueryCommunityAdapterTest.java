package hotil.baemo.domains.community.adapter.output.query;

import hotil.baemo.core.util.BaeMoTimeUtil;
import hotil.baemo.domains.comment.adapter.output.persistence.entity.CommentEntity;
import hotil.baemo.domains.comment.adapter.output.persistence.repository.CommentJpaRepository;
import hotil.baemo.domains.comment.adapter.output.persistence.repository.CommentLikeJpaRepository;
import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityEntity;
import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityImageEntity;
import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityLikeEntity;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityImageJpaRepository;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityJpaRepository;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityLikeJpaRepository;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.users.adapter.output.persistence.entity.BaeMoUserEntity;
import hotil.baemo.domains.users.adapter.output.persistence.repository.BaeMoUserJpaRepository;
import hotil.baemo.support.base.RepositoryTestBaseSupport;
import net.jqwik.api.Arbitraries;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static hotil.baemo.support.util.BaemoTestEnvironment.REPOSITORY_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;

@Import(QueryCommunityAdapter.class)
class QueryCommunityAdapterTest extends RepositoryTestBaseSupport {
    @Autowired
    private BaeMoUserJpaRepository baeMoUserJpaRepository;
    @Autowired
    private CommunityJpaRepository communityJpaRepository;
    @Autowired
    private CommunityImageJpaRepository communityImageJpaRepository;
    @Autowired
    private CommunityLikeJpaRepository communityLikeJpaRepository;
    @Autowired
    private CommentJpaRepository commentJpaRepository;
    @Autowired
    private CommentLikeJpaRepository commentLikeJpaRepository;

    @Autowired
    private QueryCommunityAdapter queryCommunityAdapter;

    private List<CommunityEntity> communityEntityList;
    private List<CommunityImageEntity> communityImageEntityList;
    private List<CommunityLikeEntity> communityLikeByUser;
    private List<Long> communityLikeCountList;
    private List<Long> commentCountList;

    private Long userId;
    private int communitySize;

    @BeforeEach
    void set() {
        clear();

        this.communitySize = Arbitraries.integers().between(1, 50).sample();

        setUpUser();
        setUpCommunity();
        setUpCommunityThumbnail();
        setUpCommunityIsLikedByUser();
        setUpCommunityLikeList();
        setUpCommentCount();
    }

    @AfterEach
    void clear() {
        baeMoUserJpaRepository.deleteAll();
        communityJpaRepository.deleteAll();
        communityImageJpaRepository.deleteAll();
        communityLikeJpaRepository.deleteAll();
        commentJpaRepository.deleteAll();
        commentLikeJpaRepository.deleteAll();
    }

    @RepeatedTest(REPOSITORY_COUNT)
    void 조회에_성공할_것이다() {
        final var result = queryCommunityAdapter.read(new CommunityUserId(userId),
            PageRequest.of(0, communitySize, Sort.Direction.ASC, "createdAt"),
            null
        );

        Assertions.assertThat(result.list().size()).isEqualTo(communitySize);

        final var resultList = result.list();

        IntStream.range(0, communitySize)
            .forEach(i -> {
                final var communityPreview = resultList.get(i);

                final var communityEntity = communityEntityList.get(i);
                final var communityImageEntity = communityImageEntityList.get(i);

                final var isLikedByUser = communityLikeByUser.get(i).getIsLike();
                final var commentCount = commentCountList.get(i);
                final var likeCount = communityLikeCountList.get(i);

                assertAll(
                    () -> Assertions.assertThat(communityEntity.getCommunityId()).isEqualTo(communityPreview.communityId()),
                    () -> Assertions.assertThat(communityEntity.getCommunityCategory()).isEqualTo(communityPreview.category()),
                    () -> Assertions.assertThat(communityEntity.getTitle()).isEqualTo(communityPreview.title()),
                    () -> Assertions.assertThat(communityEntity.getContent()).isEqualTo(communityPreview.content()),
                    () -> Assertions.assertThat(communityEntity.getCreatedAt()).isEqualTo(BaeMoTimeUtil.convert(communityPreview.createdAt())),
                    () -> Assertions.assertThat(communityEntity.getUpdatedAt()).isEqualTo(BaeMoTimeUtil.convert(communityPreview.updatedAt())),

                    () -> Assertions.assertThat(communityImageEntity.getImage()).isEqualTo(communityPreview.thumbnail()),
                    () -> Assertions.assertThat(isLikedByUser).isEqualTo(communityPreview.isLikedByUser()),
                    () -> Assertions.assertThat(commentCount).isEqualTo(communityPreview.commentCount()),
                    () -> Assertions.assertThat(likeCount).isEqualTo(communityPreview.likeCount())
                );
            });
    }

    @Test
    void 상세보기_조회에_성공할_것이다() {
        final var targetCommunityId = this.communityEntityList.get(0).getCommunityId();
        final var likeCount = communityLikeCountList.get(0);
        final var isLikeByUser = communityLikeByUser.get(0).getIsLike();

        final var communityId = new CommunityId(targetCommunityId);
        final var result = queryCommunityAdapter.readDetails(
            communityId,
            new CommunityUserId(this.userId)
        );

        final var communityEntity = communityJpaRepository.loadById(communityId);
        final var baeMoUserEntity = baeMoUserJpaRepository.loadById(communityEntity.getWriter());
        assertAll(
            () -> Assertions.assertThat(result.communityId()).isEqualTo(communityEntity.getCommunityId()),

            () -> Assertions.assertThat(result.writerId()).isEqualTo(communityEntity.getWriter()),
            () -> Assertions.assertThat(result.profileImage()).isEqualTo(baeMoUserEntity.getProfileImage()),
            () -> Assertions.assertThat(result.nickname()).isEqualTo(baeMoUserEntity.getNickname()),

            () -> Assertions.assertThat(result.category()).isEqualTo(communityEntity.getCommunityCategory()),
            () -> Assertions.assertThat(result.title()).isEqualTo(communityEntity.getTitle()),
            () -> Assertions.assertThat(result.content()).isEqualTo(communityEntity.getContent()),

            () -> Assertions.assertThat(result.likeCount()).isEqualTo(likeCount),
            () -> Assertions.assertThat(result.viewCount()).isEqualTo(communityEntity.getViewCount()),

            () -> Assertions.assertThat(BaeMoTimeUtil.convert(result.createdAt())).isEqualTo(communityEntity.getCreatedAt()),
            () -> Assertions.assertThat(BaeMoTimeUtil.convert(result.updatedAt())).isEqualTo(communityEntity.getUpdatedAt()),

            () -> Assertions.assertThat(result.isLikedByUser()).isEqualTo(isLikeByUser)
        );
    }

    @Test
    void 이미지_목록_조회에_성공할_것이다() {
        final var targetIndex = 0;
        final var communityId = this.communityEntityList.get(targetIndex).getCommunityId();
        final int imageSize = Arbitraries.integers().between(10, 50).sample();

        for (int i = 1; i <= imageSize; i++) {
            communityImageJpaRepository.save(CommunityImageEntity.builder()
                .communityId(communityId)
                .image("test_thumbnail_image" + communityId)
                .orderNumber((long) i + 1)
                .isDelete(false)
                .isThumbnail(false)
                .build());
        }

        final var savedImageList = communityImageJpaRepository.findAllByCommunityId(communityId);
        final var thumbnailCount = 1;
        final var result = queryCommunityAdapter.loadImageList(new CommunityId(communityId));

        assertAll(
            () -> Assertions.assertThat(result.getList().size()).isEqualTo(imageSize + thumbnailCount),
            () -> Assertions.assertThat(result.getList().get(targetIndex).communityImageThumbnail().isThumbnail()).isTrue()
        );

        IntStream.range(0, savedImageList.size())
            .forEach(i -> {
                final var communityImageEntity = savedImageList.get(i);
                final var resultDetails = result.getList().get(i);

                assertAll(
                    () -> Assertions.assertThat(communityImageEntity.getImage()).isEqualTo(resultDetails.getImageString()),
                    () -> Assertions.assertThat(communityImageEntity.getIsThumbnail()).isEqualTo(resultDetails.isThumbnailBoolean()),
                    () -> Assertions.assertThat(communityImageEntity.getOrderNumber()).isEqualTo(resultDetails.getOrderNumberLong())
                );
            });
    }

    private void setUpUser() {
        this.userId = baeMoUserJpaRepository.save(monkey.giveMeBuilder(BaeMoUserEntity.class)
            .setNull("id")
            .set("isDel", false)
            .sample()).getId();
    }

    private void setUpCommunity() {
        this.communityEntityList = new ArrayList<>();

        IntStream.range(0, communitySize)
            .forEach(e -> {
                communityEntityList.add(
                    communityJpaRepository.save(monkey.giveMeBuilder(CommunityEntity.class)
                        .setNull("communityId")
                        .set("writer", userId)
                        .set("isDelete", false)
                        .sample())
                );
            });
    }

    private void setUpCommunityThumbnail() {
        this.communityImageEntityList = new ArrayList<>();

        communityEntityList.forEach(c -> {
            communityImageEntityList.add(
                communityImageJpaRepository.save(CommunityImageEntity.builder()
                    .communityId(c.getCommunityId())
                    .image("test_thumbnail_image" + c.getCommunityId())
                    .orderNumber(1L)
                    .isDelete(false)
                    .isThumbnail(true)
                    .build())
            );
        });
    }

    private void setUpCommunityIsLikedByUser() {
        this.communityLikeByUser = new ArrayList<>();

        communityEntityList.forEach(c ->
            communityLikeByUser.add(
                communityLikeJpaRepository.save(monkey.giveMeBuilder(CommunityLikeEntity.class)
                    .setNull("id")
                    .set("userId", userId)
                    .set("communityId", c.getCommunityId())
                    .sample())
            )
        );
    }

    private void setUpCommunityLikeList() {
        this.communityLikeCountList = new ArrayList<>();

        IntStream.range(0, communityEntityList.size())
            .forEach(i -> {
                final var communityEntity = communityEntityList.get(i);
                final var likeByUser = communityLikeByUser.get(i);
                long likeSize = Arbitraries.longs().between(1, 50).sample();

                for (int j = 1; j <= likeSize; j++) {
                    communityLikeJpaRepository.save(
                        CommunityLikeEntity.builder()
                            .userId(userId + j)
                            .isLike(true)
                            .communityId(communityEntity.getCommunityId())
                            .build()
                    );
                }

                if (likeByUser.getIsLike()) {
                    likeSize++;
                }

                communityLikeCountList.add(likeSize);
            });
    }

    private void setUpCommentCount() {
        this.commentCountList = new ArrayList<>();

        communityEntityList.forEach(c -> {
            final long commentSize = Arbitraries.longs().between(1, 50).sample();
            for (int i = 0; i < commentSize; i++) {
                commentJpaRepository.save(monkey.giveMeBuilder(CommentEntity.class)
                    .setNull("commentId")
                    .set("writerId", userId)
                    .set("communityId", c.getCommunityId())
                    .set("isDelete", false)
                    .sample());
            }

            commentCountList.add(commentSize);
        });
    }
}