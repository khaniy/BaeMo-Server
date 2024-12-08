package hotil.baemo.domains.clubs.adapter.input.rest.comment;

import hotil.baemo.domains.clubs.adapter.output.persist.comment.entity.ClubPostCommentLikeEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.comment.repository.ClubPostCommentLikeJpaRepository;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.club.ClubPostSupport;
import hotil.baemo.support.domain.club.ClubSupport;
import hotil.baemo.support.domain.club.ImportRepliesServiceSupport;
import hotil.baemo.support.domain.user.UserSupport;
import hotil.baemo.support.util.BaemoTestEnvironment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LikeClubPostCommentApiTest extends ControllerTestBaseSupport {
    @Autowired
    private ClubPostCommentLikeJpaRepository repository;

    @Autowired
    private UserSupport userSupport;
    @Autowired
    private ImportRepliesServiceSupport importRepliesServiceSupport;

    @Autowired
    private ClubPostSupport clubPostSupport;

    @Autowired
    private ClubSupport clubSupport;

    private Long userId;
    private Long clubsId;
    private Long postId;
    private Long commentId;

    @BeforeEach
    void set() {
        userSupport.setUpUser();
        this.userId = userSupport.getUserId();
        clubSupport.setClubsAdmin(userId);
        this.clubsId = clubSupport.getClubsId();

        this.postId = clubPostSupport.createPost(clubsId);
        this.commentId = importRepliesServiceSupport.create();
    }

    @RepeatedTest(BaemoTestEnvironment.API_COUNT)
    void 좋아요에_성공할_것이다() throws Exception {
        mockMvc.perform(patch("/api/clubs/post/{postId}/comment/{commentId}/like", this.postId, commentId))
            .andExpect(status().isOk());

        final var result = repository.findByCommentIdAndClubsUserId(commentId, userId);
        assertAll(() -> {
            Assertions.assertThat(result.isPresent()).isTrue();
            final var entity = result.get();
            Assertions.assertThat(entity.getIsLike()).isTrue();
        });
    }

    @RepeatedTest(BaemoTestEnvironment.API_COUNT)
    void 좋아요를_누른_상태에서는_취소가_될_것이다() throws Exception {
        repository.save(monkey.giveMeBuilder(ClubPostCommentLikeEntity.class)
            .setNull("id")
            .set("commentId", this.commentId)
            .set("clubsUserId", this.userId)
            .set("isLike", true)
            .sample());

        mockMvc.perform(patch("/api/clubs/post/{postId}/comment/{commentId}/like", this.postId, commentId))
            .andExpect(status().isOk());
        final var result = repository.findByCommentIdAndClubsUserId(commentId, userId);

        assertAll(() -> {
            Assertions.assertThat(result.isPresent()).isFalse();
        });
    }
}