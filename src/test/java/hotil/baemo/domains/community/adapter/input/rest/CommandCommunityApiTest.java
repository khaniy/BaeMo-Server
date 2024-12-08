package hotil.baemo.domains.community.adapter.input.rest;

import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.community.adapter.input.rest.dto.request.CommunityRequest;
import hotil.baemo.domains.community.adapter.input.rest.dto.response.CommunityResponse;
import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityImageEntity;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityImageJpaRepository;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityJpaRepository;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.community.CommunityImageSupport;
import hotil.baemo.support.domain.community.CommunitySupport;
import hotil.baemo.support.domain.user.UserSupport;
import net.jqwik.api.Arbitraries;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static hotil.baemo.core.common.response.ResponseCode.COMMUNITY_NOT_FOUND;
import static hotil.baemo.support.util.BaemoTestEnvironment.API_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommandCommunityApiTest extends ControllerTestBaseSupport {
    @Autowired
    private CommunityJpaRepository communityJpaRepository;
    @Autowired
    private CommunityImageJpaRepository communityImageJpaRepository;
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private CommunitySupport importCommunityServiceSupport;
    @Autowired
    private CommunityImageSupport communityImageSupport;
    private Long userId;
    private Long communityId;

    @BeforeEach
    void set() {
        userSupport.setUpUser();
        this.userId = userSupport.getUserId();

        this.communityId = importCommunityServiceSupport.create(userId);
    }

    @RepeatedTest(API_COUNT)
    void 커뮤니티_생성에_성공할_것이다() throws Exception {
        final var imageSize = Arbitraries.integers().between(0, 9).sample();
        final List<CommunityRequest.ImageDetails> list = new ArrayList<>();
        list.add(monkey.giveMeBuilder(CommunityRequest.ImageDetails.class)
            .set("isThumbnail", true)
            .sample());

        for (int i = 0; i < imageSize; i++) {
            list.add(monkey.giveMeBuilder(CommunityRequest.ImageDetails.class)
                .set("isThumbnail", false)
                .sample());
        }

        final var request = monkey.giveMeBuilder(CommunityRequest.CreateDTO.class)
            .set("imageList", CommunityRequest.ImageList.builder().imageList(list).build())
            .sample();

        final var result = mockMvc.perform(post("/api/communities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.payload.communityId").isNumber())
            .andReturn().getResponse().getContentAsString();

        assertAll(() -> {
            final var responseCommunityId = readResult(result, CommunityResponse.CreateDTO.class).communityId();
            final var savedCommunity = communityJpaRepository.findById(responseCommunityId).orElseThrow();
            communityImageJpaRepository.findAllByCommunityId(responseCommunityId);

            Assertions.assertThat(savedCommunity.getTitle()).isEqualTo(request.title());
            Assertions.assertThat(savedCommunity.getContent()).isEqualTo(request.content());
            Assertions.assertThat(savedCommunity.getCommunityCategory()).isEqualTo(request.category());
        });
    }

    @RepeatedTest(API_COUNT)
    void 커뮤니티_수정에_성공할_것이다() throws Exception {
        final var imageSize = Arbitraries.integers().between(0, 9).sample();
        final List<CommunityRequest.ImageDetails> list = new ArrayList<>();
        list.add(monkey.giveMeBuilder(CommunityRequest.ImageDetails.class)
            .set("isThumbnail", true)
            .sample());

        for (int i = 0; i < imageSize; i++) {
            list.add(monkey.giveMeBuilder(CommunityRequest.ImageDetails.class)
                .set("isThumbnail", false)
                .sample());
        }

        final var request = monkey.giveMeBuilder(CommunityRequest.UpdateDTO.class)
            .set("communityId", communityId)
            .set("imageList", CommunityRequest.ImageList.builder().imageList(list).build())
            .sample();

        mockMvc.perform(put("/api/communities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());

        assertAll(() -> {
            final var updateCommunity = communityJpaRepository.loadById(communityId);

            Assertions.assertThat(updateCommunity.getTitle()).isEqualTo(request.title());
            Assertions.assertThat(updateCommunity.getContent()).isEqualTo(request.content());
            Assertions.assertThat(updateCommunity.getCommunityCategory()).isEqualTo(request.category());
        });
    }

    @RepeatedTest(API_COUNT)
    void 커뮤니티_삭제에_성공할_것이다() throws Exception {
        communityImageSupport.create(communityId, true);
        final var imageSize = Arbitraries.integers().between(0, 9).sample();
        for (int i = 0; i < imageSize; i++) {
            communityImageSupport.create(communityId, false);
        }

        final var id = new CommunityId(communityId);
        final var beforeCommunityImageEntityList = communityImageJpaRepository.findAllByCommunityId(communityId);

        mockMvc.perform(delete("/api/communities/{communityId}", this.communityId))
            .andExpect(status().isOk());

        assertAll(() -> {
            Assertions.assertThatThrownBy(() -> communityJpaRepository.loadById(id))
                .isInstanceOf(CustomException.class)
                .hasMessage(COMMUNITY_NOT_FOUND.name());

            final var afterCommunityImageEntityList = communityImageJpaRepository.findAllByCommunityId(communityId);

            Assertions.assertThat(beforeCommunityImageEntityList.stream()
                .filter(c -> !c.getIsThumbnail())
                .toList().size()).isEqualTo(imageSize);

            Assertions.assertThat(beforeCommunityImageEntityList.stream()
                .filter(CommunityImageEntity::getIsThumbnail)
                .toList().size()).isEqualTo(1);

            Assertions.assertThat(afterCommunityImageEntityList.size()).isZero();
        });
    }
}