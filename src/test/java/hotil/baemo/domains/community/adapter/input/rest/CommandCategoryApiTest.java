package hotil.baemo.domains.community.adapter.input.rest;

import hotil.baemo.domains.community.adapter.input.rest.dto.request.CategoryRequest;
import hotil.baemo.domains.community.adapter.output.persistence.mapper.CategoryMapper;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CategoryJpaRepository;
import hotil.baemo.domains.community.domain.value.CommunityCategory;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.HashSet;

import static hotil.baemo.support.util.BaemoTestEnvironment.API_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommandCategoryApiTest extends ControllerTestBaseSupport {
    @Autowired
    private CategoryJpaRepository categoryJpaRepository;
    @Autowired
    private UserSupport userSupport;
    private Long userId;

    @BeforeEach
    void set() {
        userSupport.setUpUser();
        this.userId = userSupport.getUserId();
    }

    @RepeatedTest(API_COUNT)
    void 커뮤니티_카테고리_구독에_성공할_것이다() throws Exception {
        final var sampleCommunityCategoryList = monkey.giveMe(CommunityCategory.class, 5).stream().distinct().toList();
        final var request = CategoryRequest.SubscribeDTO.builder()
            .categoryList(sampleCommunityCategoryList)
            .build();

        mockMvc.perform(post("/api/communities/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());

        assertAll(() -> {
            final var requestCategoryList = request.toCategoryList();
            final var categoryEntity = categoryJpaRepository.findByUserId(userId);
            final var convert = CategoryMapper.convert(categoryEntity);

            final var requestCategoryListToStrings = requestCategoryList.stream().map(Enum::name).toList();
            final var convertToStrings = convert.stream().map(Enum::name).toList();

            Assertions.assertThat(requestCategoryListToStrings.size()).isEqualTo(convertToStrings.size());

            Assertions.assertThat(new HashSet<>(convertToStrings))
                .isEqualTo(new HashSet<>(requestCategoryListToStrings));
        });
    }
}