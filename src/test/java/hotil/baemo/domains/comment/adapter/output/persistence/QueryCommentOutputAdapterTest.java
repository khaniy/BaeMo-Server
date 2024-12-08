package hotil.baemo.domains.comment.adapter.output.persistence;

import hotil.baemo.domains.comment.adapter.output.persistence.entity.CommentEntity;
import hotil.baemo.domains.comment.adapter.output.persistence.entity.CommentLikeEntity;
import hotil.baemo.domains.comment.adapter.output.persistence.repository.CommentJpaRepository;
import hotil.baemo.domains.comment.adapter.output.persistence.repository.CommentLikeJpaRepository;
import hotil.baemo.domains.comment.domain.entity.CommentCommunityId;
import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityEntity;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityJpaRepository;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.users.adapter.output.persistence.entity.BaeMoUserEntity;
import hotil.baemo.domains.users.adapter.output.persistence.repository.BaeMoUserJpaRepository;
import hotil.baemo.support.base.RepositoryTestBaseSupport;
import net.jqwik.api.Arbitraries;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static hotil.baemo.support.util.BaemoTestEnvironment.REPOSITORY_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;

@Import(QueryCommentOutputAdapter.class)
class QueryCommentOutputAdapterTest extends RepositoryTestBaseSupport {
    @Autowired
    private BaeMoUserJpaRepository baeMoUserJpaRepository;
    @Autowired
    private CommunityJpaRepository communityJpaRepository;
    @Autowired
    private CommentJpaRepository commentJpaRepository;
    @Autowired
    private CommentLikeJpaRepository commentLikeJpaRepository;

    @Autowired
    private QueryCommentOutputAdapter queryCommentOutputAdapter;
    private BaeMoUserEntity user;
    private Long userid;
    private CommentEntity saved;
    private Long communityId;

    private int commentSize;
    private int likeSize;
    private boolean isLikedByUser;

    @BeforeEach
    void set() {
        this.commentSize = Arbitraries.integers().between(1, 50).sample();
        this.likeSize = Arbitraries.integers().between(1, 50).sample();

        setUpUser();
        setUpCommunity();
        setUpComment();
        setUpCommentList();
        setUpCommentLikeList();
    }

    @RepeatedTest(REPOSITORY_COUNT)
    void 조회에_성공할_것이다() {
        final var result = queryCommentOutputAdapter.retrieveCommentListByCommunity(
            new CommentCommunityId(this.communityId),
            new CommunityUserId(this.userid),
            PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Order.asc("createdAt")))
        );

        assertAll(() -> {
            Assertions.assertThat(result.list().size()).isEqualTo(this.commentSize + 1);

            final var commentDetails = result.list().get(0);

            Assertions.assertThat(commentDetails.getCommentId()).isEqualTo(this.saved.getCommentId());
            Assertions.assertThat(commentDetails.getCommunityId()).isEqualTo(this.saved.getCommunityId());
            Assertions.assertThat(commentDetails.getPreCommentId()).isEqualTo(this.saved.getPreCommentId());
            Assertions.assertThat(commentDetails.getContent()).isEqualTo(this.saved.getContent());
            Assertions.assertThat(commentDetails.getIsDelete()).isEqualTo(this.saved.getIsDelete());
            Assertions.assertThat(commentDetails.getCreatedAt()).isEqualTo(this.saved.getCreatedAt());
            Assertions.assertThat(commentDetails.getUpdatedAt()).isEqualTo(this.saved.getUpdatedAt());
            Assertions.assertThat(commentDetails.getWriterId()).isEqualTo(this.saved.getWriterId());

            Assertions.assertThat(commentDetails.getLikeCount()).isEqualTo(this.likeSize);

            Assertions.assertThat(commentDetails.getNickname()).isEqualTo(this.user.getNickname());
            Assertions.assertThat(commentDetails.getRealName()).isEqualTo(this.user.getRealName());
            Assertions.assertThat(commentDetails.getProfileImage()).isEqualTo(this.user.getProfileImage());

            Assertions.assertThat(commentDetails.getIsLikedByUser()).isEqualTo(this.isLikedByUser);
        });
    }

    private void setUpUser() {
        this.user = baeMoUserJpaRepository.save(monkey.giveMeBuilder(BaeMoUserEntity.class)
            .setNull("id")
            .set("isDel", false)
            .sample());
        this.userid = this.user.getId();
    }

    private void setUpCommunity() {
        this.communityId = communityJpaRepository.save(monkey.giveMeBuilder(CommunityEntity.class)
                .setNull("communityId")
                .set("writer", userid)
                .set("isDelete", false)
                .sample())
            .getCommunityId();
    }

    private void setUpComment() {
        this.saved = commentJpaRepository.save(monkey.giveMeBuilder(CommentEntity.class)
            .setNull("commentId")
            .set("writerId", userid)
            .set("communityId", communityId)
            .set("isDelete", false)
            .sample());
    }

    private void setUpCommentLikeList() {
        for (int i = 0; i < likeSize; i++) {
            commentLikeJpaRepository.save(monkey.giveMeBuilder(CommentLikeEntity.class)
                .setNull("commentLikeId")
                .set("commentId", saved.getCommentId())
                .set("userId", userid + i)
                .set("isLike", true)
                .sample()
            );
        }

        this.isLikedByUser = true;
    }

    private void setUpCommentList() {
        for (int i = 0; i < commentSize; i++) {
            commentJpaRepository.save(monkey.giveMeBuilder(CommentEntity.class)
                .setNull("commentId")
                .set("writerId", userid)
                .set("communityId", communityId)
                .set("isDelete", false)
                .sample());
        }
    }
}