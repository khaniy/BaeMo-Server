package hotil.baemo.domains.comment.adapter.input.rest.command;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.comment.adapter.input.rest.dto.request.CommentRequest;
import hotil.baemo.domains.comment.adapter.input.rest.dto.response.CommentResponse;
import hotil.baemo.domains.comment.adapter.output.persistence.entity.CommentEntity;
import hotil.baemo.domains.comment.adapter.output.persistence.repository.CommentJpaRepository;
import hotil.baemo.domains.comment.domain.entity.CommentId;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.community.CommunitySupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static hotil.baemo.support.util.BaemoTestEnvironment.API_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommandCommentApiTest extends ControllerTestBaseSupport {
    @Autowired
    private CommentJpaRepository repository;

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private CommunitySupport importCommunityServiceSupport;
    private Long userId;
    private Long communityId;

    @BeforeEach
    void set() {
        userSupport.setUpUser();
        this.userId = userSupport.getUserId();

        this.communityId = importCommunityServiceSupport.create(userId);
    }

    @RepeatedTest(API_COUNT)
    void 코멘트_생성에_성공할_것이다() throws Exception {
        final var request = monkey.giveMeBuilder(CommentRequest.CreateDTO.class)
            .sample();

        final var result = mockMvc.perform(post("/api/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        assertAll(() -> {
            final var response = readResult(result, CommentResponse.CreateDTO.class);
            final var commentEntity = repository.load(new CommentId(response.commentId()));

            Assertions.assertThat(commentEntity.getContent()).isEqualTo(request.content());
            Assertions.assertThat(commentEntity.getWriterId()).isEqualTo(userId);
            Assertions.assertThat(commentEntity.getPreCommentId()).isEqualTo(request.preCommentId());
        });
    }

    @RepeatedTest(API_COUNT)
    void 코멘트_수정에_성공할_것이다() throws Exception {
        final var sampleCommentEntity = monkey.giveMeBuilder(CommentEntity.class)
            .setNull("commentId")
            .set("writerId", userId)
            .set("isDelete", false)
            .sample();

        final var before = repository.save(sampleCommentEntity);

        final var request = monkey.giveMeBuilder(CommentRequest.UpdateDTO.class)
            .sample();

        mockMvc.perform(put("/api/comment/{commentId}", before.getCommentId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());

        assertAll(() -> {
            final var after = repository.load(new CommentId(before.getCommentId()));
            Assertions.assertThat(after.getContent()).isEqualTo(request.newContent());
        });
    }

    @RepeatedTest(API_COUNT)
    void 코멘트_삭제에_성공할_것이다() throws Exception {
        final var sampleCommentEntity = monkey.giveMeBuilder(CommentEntity.class)
            .setNull("commentId")
            .set("writerId", userId)
            .set("isDelete", false)
            .sample();

        final var before = repository.save(sampleCommentEntity);

        mockMvc.perform(delete("/api/comment/{commentId}", before.getCommentId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        assertAll(() -> {
            Assertions.assertThatThrownBy(() -> repository.load(new CommentId(before.getCommentId())))
                .isInstanceOf(CustomException.class)
                .hasMessage(ResponseCode.COMMENT_NOT_FOUND.name());
        });
    }
}