package hotil.baemo.domains.comment.adapter.input.rest.query;

import hotil.baemo.domains.comment.adapter.input.rest.dto.response.CommentResponse;
import hotil.baemo.domains.comment.adapter.output.persistence.entity.CommentEntity;
import hotil.baemo.domains.comment.adapter.output.persistence.entity.CommentLikeEntity;
import hotil.baemo.domains.comment.adapter.output.persistence.repository.CommentJpaRepository;
import hotil.baemo.domains.comment.adapter.output.persistence.repository.CommentLikeJpaRepository;
import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityEntity;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityJpaRepository;
import hotil.baemo.domains.users.adapter.output.persistence.entity.BaeMoUserEntity;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.user.UserSupport;
import net.jqwik.api.Arbitraries;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;

import static hotil.baemo.support.util.BaemoTestEnvironment.API_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QueryCommentApiTest extends ControllerTestBaseSupport {

    @Autowired
    private CommunityJpaRepository communityJpaRepository;
    @Autowired
    private CommentJpaRepository commentJpaRepository;
    @Autowired
    private CommentLikeJpaRepository commentLikeJpaRepository;

    @Autowired
    private UserSupport userSupport;

    private BaeMoUserEntity user;
    private Long userid;
    private CommentEntity saved;
    private Long communityId;

    private int commentSize;
    private int likeSize;

    @BeforeEach
    void set() {
        this.user = userSupport.setUpUser();
        this.userid = userSupport.getUserId();

        this.commentSize = Arbitraries.integers().between(1, 50).sample();
        this.likeSize = Arbitraries.integers().between(1, 50).sample();

        this.communityId = communityJpaRepository.save(monkey.giveMeBuilder(CommunityEntity.class)
                .setNull("communityId")
                .set("writer", userid)
                .set("content", "testContent")
                .set("isDelete", false)
                .sample())
            .getCommunityId();

        this.saved = commentJpaRepository.save(monkey.giveMeBuilder(CommentEntity.class)
            .setNull("commentId")
            .set("writerId", userid)
            .set("communityId", communityId)
            .set("content", "testContent")
            .set("isDelete", false)
            .sample());

        for (int i = 1; i < commentSize; i++) {
            commentJpaRepository.save(monkey.giveMeBuilder(CommentEntity.class)
                .setNull("commentId")
                .set("writerId", userid)
                .set("communityId", communityId)
                .set("content", "testContent")
                .set("isDelete", false)
                .sample());
        }

        for (int i = 0; i < likeSize; i++) {
            commentLikeJpaRepository.save(monkey.giveMeBuilder(CommentLikeEntity.class)
                .setNull("commentLikeId")
                .set("commentId", saved.getCommentId())
                .set("userId", userid + i)
                .set("isLike", true)
                .sample()
            );
        }
    }

    @RepeatedTest(API_COUNT)
    void 조회에_성공할_것이다() throws Exception {
        final var result = mockMvc.perform(get("/api/comment/{communityId}", communityId))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        System.out.println(result);

        final var response = readResult(result, CommentResponse.CommentDetailsList.class);
        final var commentDetails = response.list().get(0);

        assertAll(
            () -> Assertions.assertThat(response.list().size()).isLessThanOrEqualTo(30),

            () -> Assertions.assertThat(commentDetails.commentId()).isEqualTo(saved.getCommentId()),
            () -> Assertions.assertThat(commentDetails.communityId()).isEqualTo(saved.getCommunityId()),
            () -> Assertions.assertThat(commentDetails.preCommentId()).isEqualTo(saved.getPreCommentId()),
            () -> Assertions.assertThat(commentDetails.content()).isEqualTo(saved.getContent()),
            () -> Assertions.assertThat(commentDetails.isDelete()).isEqualTo(saved.getIsDelete()),
            () -> Assertions.assertThat(commentDetails.createdAt()).isEqualTo(saved.getCreatedAt()),
            () -> Assertions.assertThat(commentDetails.updatedAt()).isEqualTo(saved.getUpdatedAt()),
            () -> Assertions.assertThat(commentDetails.writerId()).isEqualTo(saved.getWriterId()),

            () -> Assertions.assertThat(commentDetails.likeCount()).isEqualTo(likeSize),

            () -> Assertions.assertThat(commentDetails.nickname()).isEqualTo(user.getNickname()),
            () -> Assertions.assertThat(commentDetails.realName()).isEqualTo(user.getRealName()),
            () -> Assertions.assertThat(commentDetails.profileImage()).isEqualTo(user.getProfileImage()),
            () -> Assertions.assertThat(commentDetails.isLikedByUser()).isTrue()
        );
    }

    @Test
    void 페이징_처리() throws Exception {
        final var result1 = mockMvc.perform(get("/api/comment/{communityId}?page=0&size=10&sort=commentId,asc", communityId))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        final var result2 = mockMvc.perform(get("/api/comment/{communityId}?page=0&size=10&sort=commentId,desc", communityId))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        final var result3 = mockMvc.perform(get("/api/comment/{communityId}?page=0&size=10&sort=likeCount,asc", communityId))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        final var result4 = mockMvc.perform(get("/api/comment/{communityId}?page=0&size=10&sort=likeCount,desc&sort=commentId,asc", communityId))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);
    }
}