package hotil.baemo.domains.comment.adapter.input.rest.command;

import hotil.baemo.domains.comment.adapter.output.persistence.entity.CommentLikeEntity;
import hotil.baemo.domains.comment.adapter.output.persistence.repository.CommentJpaRepository;
import hotil.baemo.domains.comment.adapter.output.persistence.repository.CommentLikeJpaRepository;
import hotil.baemo.domains.comment.domain.entity.CommentId;
import hotil.baemo.domains.comment.domain.entity.CommentWriter;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.comment.CommentSupport;
import hotil.baemo.support.domain.community.CommunitySupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

import static hotil.baemo.support.util.BaemoTestEnvironment.API_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommandLikeApiTest extends ControllerTestBaseSupport {
    private static final String API = "/api/comment/like/{commentId}";
    @Autowired
    private CommentJpaRepository commentJpaRepository;
    @Autowired
    private CommentLikeJpaRepository commentLikeJpaRepository;

    @Autowired
    private UserSupport userSupport;
    @Autowired
    private CommunitySupport importCommunityServiceSupport;

    private Long userId;
    private Long commentId;

    @BeforeEach
    void set() {
        userSupport.setUpUser();
        this.userId = userSupport.getUserId();
        final var communityId = importCommunityServiceSupport.create(this.userId);

        final var commentEntity = CommentSupport.builder()
            .set("communityId", communityId)
            .set("writerId", this.userId + 1)
            .set("isDelete", false)
            .sample();

        final var savedCommentEntity = commentJpaRepository.save(commentEntity);
        this.commentId = savedCommentEntity.getCommentId();

    }

    @RepeatedTest(API_COUNT)
    void 좋아요에_성공할_것이다() throws Exception {
        mockMvc.perform(post(API, this.commentId))
            .andExpect(status().isOk());

        assertAll(() -> {
            final var likeEntity = commentLikeJpaRepository.load(new CommentWriter(this.userId), new CommentId(this.commentId));
            Assertions.assertThat(likeEntity.getIsLike()).isTrue();
        });
    }

    @RepeatedTest(API_COUNT)
    void 좋아요_요청마다_상태가_변경될_것이다() throws Exception {
        final var before = commentLikeJpaRepository.save(monkey.giveMeBuilder(CommentLikeEntity.class)
            .setNull("commentLikeId")
            .set("commentId", this.commentId)
            .set("userId", this.userId)
            .sample());

        mockMvc.perform(post(API, this.commentId))
            .andExpect(status().isOk());

        assertAll(() -> {
            final var after = commentLikeJpaRepository.load(new CommentWriter(userId), new CommentId(this.commentId));
            Assertions.assertThat(after.getIsLike()).isNotEqualTo(before.getIsLike());
        });
    }
}