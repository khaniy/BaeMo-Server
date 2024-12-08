package hotil.baemo.domains.clubs.adapter.input.rest.comment;

import hotil.baemo.domains.clubs.application.dto.QClubPostCommentDTO;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.club.ClubPostSupport;
import hotil.baemo.support.domain.club.ClubSupport;
import hotil.baemo.support.domain.club.ImportRepliesServiceSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QueryClubPostCommentApiTest extends ControllerTestBaseSupport {
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private ClubSupport clubSupport;
    @Autowired
    private ClubPostSupport clubPostSupport;
    @Autowired
    private ImportRepliesServiceSupport importRepliesServiceSupport;

    private Long userId;
    private Long clubsId;
    private Long postId;

    @BeforeEach
    void set() {
        userSupport.setUpUser();
        this.userId = userSupport.getUserId();
        clubSupport.setClubsAdmin(userId);
        this.clubsId = clubSupport.getClubsId();
        this.postId = clubPostSupport.createPost(clubsId, userId);
    }

    @Test
    void 조회에_성공할_것이다() throws Exception {
        final List<Long> repliesIdList = new ArrayList<>();
        repliesIdList.add(importRepliesServiceSupport.create(postId, userId));
        repliesIdList.add(importRepliesServiceSupport.create(postId, userId));
        repliesIdList.add(importRepliesServiceSupport.create(postId, userId));
        repliesIdList.add(importRepliesServiceSupport.create(postId, userId));

        final var result = super.mockMvc.perform(get("/api/clubs/post/{postId}/comment", postId))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        final var response = super.readResult(result, QClubPostCommentDTO.CommentDetailList.class);

        assertAll(() -> {
            response.list().forEach(e -> {
                Assertions.assertThat(repliesIdList.contains(e.commentId())).isTrue();
            });
        });
    }
}