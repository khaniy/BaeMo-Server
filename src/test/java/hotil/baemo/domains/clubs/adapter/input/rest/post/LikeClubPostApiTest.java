package hotil.baemo.domains.clubs.adapter.input.rest.post;

import hotil.baemo.domains.clubs.adapter.input.rest.post.dto.response.ClubsPostResponse;
import hotil.baemo.domains.clubs.adapter.output.persist.post.entity.ClubsPostLikeEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.post.repository.ClubsPostLikeJpaRepository;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.club.ClubPostSupport;
import hotil.baemo.support.domain.club.ClubSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

import static hotil.baemo.support.util.BaemoTestEnvironment.API_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LikeClubPostApiTest extends ControllerTestBaseSupport {
    @Autowired
    private ClubsPostLikeJpaRepository clubsPostLikeRepository;
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private ClubSupport clubSupport;
    @Autowired
    private ClubPostSupport clubPostSupport;
    private Long admin;
    private Long clubsId;
    private Long postId;

    @BeforeEach
    void setUp() {
        admin = userSupport.setUpUser().getId();

        clubSupport.setClubsAdmin(admin);
        clubsId = clubSupport.getClubsId();

        postId = clubPostSupport.createPost(clubsId, admin);
    }

    @RepeatedTest(API_COUNT)
    void 좋아요에_성공할_것이다() throws Exception {
        final var result = mockMvc.perform(post("/api/clubs/{clubsId}/post/{postId}/like", clubsId, postId))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        assertAll(() -> {
            final var response = readResult(result, ClubsPostResponse.LikeResult.class);
            final var found = clubsPostLikeRepository.findByClubsUserIdAndClubsPostId(admin, postId).get();
            Assertions.assertThat(found.getIsLike()).isEqualTo(response.isLike());
            Assertions.assertThat(found.getIsLike()).isTrue();
        });
    }

    @RepeatedTest(API_COUNT)
    void 좋아요는_토글로_동작할_것이다() throws Exception {
        boolean isLike = true;//new Random().nextBoolean();
        if (isLike) {
            final var before = clubsPostLikeRepository.save(
                ClubsPostLikeEntity.builder()
                    .clubsUserId(admin)
                    .clubsPostId(postId)
                    .isLike(isLike)
                    .build()
            );
        }


        final var result = mockMvc.perform(post("/api/clubs/{clubsId}/post/{postId}/like", clubsId, postId))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        assertAll(() -> {
            final var response = readResult(result, ClubsPostResponse.LikeResult.class);
            final var after = clubsPostLikeRepository.findByClubsUserIdAndClubsPostId(admin, postId);
            if (isLike) {
                Assertions.assertThat(after).isEmpty();
                Assertions.assertThat(response.isLike()).isFalse();
            } else {
                Assertions.assertThat(after).isNotEmpty();
                Assertions.assertThat(response.isLike()).isTrue();
            }
        });
    }
}