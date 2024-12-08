package hotil.baemo.domains.community.adapter.input.rest;

import hotil.baemo.domains.community.adapter.input.rest.dto.response.CommunityResponse;
import hotil.baemo.domains.community.adapter.output.persistence.entity.CategoryEntity;
import hotil.baemo.domains.community.adapter.output.persistence.mapper.CategoryMapper;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CategoryJpaRepository;
import hotil.baemo.domains.community.domain.value.CommunityCategory;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;

import static hotil.baemo.support.util.BaemoTestEnvironment.API_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QueryCategoryApiTest extends ControllerTestBaseSupport {

    @Autowired
    private UserSupport userSupport;
    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @RepeatedTest(API_COUNT)
    void 모든_카테고리_조회에_성공할_것이다() throws Exception {
        final var result = mockMvc.perform(get("/api/communities/category"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertAll(() -> {
            final var response = readResult(result, CommunityResponse.CategoryListDTO.class);

            Assertions.assertThat(new HashSet<>(response.list()))
                .isEqualTo(new HashSet<>(CommunityCategory.getAllList()));
        });
    }

    @RepeatedTest(API_COUNT)
    void 구독중인_카테고리_조회에_성공할_것이다() throws Exception {
        userSupport.setUpUser();
        final var userId = userSupport.getUserId();
        final var categoryEntity = monkey.giveMeBuilder(CategoryEntity.class)
            .setNull("id")
            .set("userId", userId)
            .sample();
        categoryJpaRepository.save(categoryEntity);

        final var result = mockMvc.perform(get("/api/communities/category/subscribed"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertAll(() -> {
            final var response = readResult(result, CommunityResponse.CategoryListDTO.class);

            Assertions.assertThat(new HashSet<>(response.list()))
                .isEqualTo(new HashSet<>(CategoryMapper.convert(categoryEntity).stream()
                    .map(CommunityCategory::getDescription)
                    .toList()));
        });
    }
}