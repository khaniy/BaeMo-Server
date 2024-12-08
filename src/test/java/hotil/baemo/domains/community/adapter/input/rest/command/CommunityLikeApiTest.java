package hotil.baemo.domains.community.adapter.input.rest.command;

import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityLikeEntity;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityLikeJpaRepository;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.community.CommunityLikeSupport;
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

class CommunityLikeApiTest extends ControllerTestBaseSupport {

    @Autowired
    private UserSupport userSupport;
    @Autowired
    private CommunitySupport communitySupport;
    @Autowired
    private CommunityLikeSupport communityLikeSupport;

    @Autowired
    private CommunityLikeJpaRepository communityLikeJpaRepository;

    private Long userId;
    private Long communityId;

    @BeforeEach
    void set() {
        userSupport.setUpUser();
        this.userId = userSupport.getUserId();
        this.communityId = communitySupport.create(this.userId + 1);
    }

    @RepeatedTest(API_COUNT)
    void 좋아요에_성공할_것이다() throws Exception {
        mockMvc.perform(post("/api/communities/like/{communityId}", this.communityId))
            .andExpect(status().isOk());

        final var after = this.communityLikeJpaRepository.load(new CommunityId(this.communityId), new CommunityUserId(this.userId));

        assertAll(
            () -> Assertions.assertThat(after.getIsLike()).isTrue()
        );
    }

    @RepeatedTest(API_COUNT)
    void 좋아요_요청마다_상태가_변경될_것이다() throws Exception {
        final var before = this.communityLikeJpaRepository.save(monkey.giveMeBuilder(CommunityLikeEntity.class)
            .set("communityId", this.communityId)
            .set("userId", this.userId)
            .sample());

        mockMvc.perform(post("/api/communities/like/{communityId}", this.communityId))
            .andExpect(status().isOk());

        final var after = this.communityLikeJpaRepository.load(new CommunityId(this.communityId), new CommunityUserId(this.userId));

        assertAll(
            () -> Assertions.assertThat(before.getIsLike()).isNotEqualTo(after.getIsLike())
        );
    }

}