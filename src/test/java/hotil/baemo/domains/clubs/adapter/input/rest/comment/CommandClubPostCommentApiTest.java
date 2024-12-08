package hotil.baemo.domains.clubs.adapter.input.rest.comment;

import hotil.baemo.domains.clubs.adapter.input.rest.comment.dto.request.CommentRequestDTO;
import hotil.baemo.domains.clubs.adapter.output.persist.comment.entity.ClubPostCommentEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.comment.repository.ClubPostCommentJpaRepository;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.club.ClubPostSupport;
import hotil.baemo.support.domain.club.ClubSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class CommandClubPostCommentApiTest extends ControllerTestBaseSupport {
    @Autowired
    private ClubPostCommentJpaRepository repository;

    @Autowired
    private UserSupport userSupport;
    @Autowired
    private ClubSupport clubSupport;
    @Autowired
    private ClubPostSupport clubPostSupport;

    private Long adminId;
    private Long clubsId;
    private Long postId;

    @BeforeEach
    void set() {
        userSupport.setUpUser();
        this.adminId = userSupport.getUserId();

        clubSupport.setClubsAdmin(adminId);
        this.clubsId = clubSupport.getClubsId();

        this.postId = clubPostSupport.createPost(clubsId);
    }

//    @RepeatedTest(BaemoTestEnvironment.API_COUNT)
    @Test
    void 모임_게시글의_댓글_생성에_성공할_것이다() throws Exception {
        userSupport.setUpUser();
        final var writerId = userSupport.getUserId();
        clubSupport.join(this.clubsId, writerId);

        final var request = monkey.giveMeBuilder(CommentRequestDTO.Create.class)
            .setNull("preCommentId")
            .sample();

        mockMvc.perform(post("/api/clubs/post/{postId}/comment", this.postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.payload.userId").value(writerId));
    }

//    @RepeatedTest(BaemoTestEnvironment.API_COUNT)
    @Test
    void 모임_게시글의_댓글_수정에_성공할_것이다() throws Exception {
        userSupport.setUpUser();
        final var writerId = userSupport.getUserId();
        clubSupport.join(this.clubsId, writerId);

        final var saved = repository.save(monkey.giveMeBuilder(ClubPostCommentEntity.class)
            .set("clubPostId", this.postId)
            .set("writerId", writerId)
            .setNull("preCommentId")
            .sample()
        );

        final var request = monkey.giveMeBuilder(CommentRequestDTO.Update.class)
            .sample();

        mockMvc.perform(put("/api/clubs/post/{postId}/comment/{commentId}", this.postId, saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());

        final var updated = repository.findById(saved.getId()).orElseThrow();
        assertAll(() -> {
            Assertions.assertThat(updated.getContent()).isEqualTo(request.newCommentContent());
        });
    }

//    @RepeatedTest(BaemoTestEnvironment.API_COUNT)
    @Test
    void 모임_게시글의_댓글_삭제에_성공할_것이다() throws Exception {
        userSupport.setUpUser();
        final var writerId = userSupport.getUserId();
        clubSupport.join(this.clubsId, writerId);

        final var saved = repository.save(monkey.giveMeBuilder(ClubPostCommentEntity.class)
            .set("clubPostId", this.postId)
            .set("writerId", writerId)
            .setNull("preCommentId")
            .sample()
        );

        mockMvc.perform(delete("/api/clubs/post/{postId}/comment/{commentId}", this.postId, saved.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        assertAll(() -> {
            Assertions.assertThat(repository.findById(saved.getId()).isPresent()).isFalse();
        });
    }
}