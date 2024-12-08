package hotil.baemo.domains.community.adapter.input.rest.query;

import hotil.baemo.core.util.BaeMoTimeUtil;
import hotil.baemo.domains.community.adapter.input.rest.dto.response.QueryResponse;
import hotil.baemo.domains.community.adapter.output.persistence.entity.CategoryEntity;
import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityImageEntity;
import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityLikeEntity;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CategoryJpaRepository;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityImageJpaRepository;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityJpaRepository;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityLikeJpaRepository;
import hotil.baemo.domains.community.application.value.query.RetrieveCommunity;
import hotil.baemo.domains.community.domain.value.CommunityCategory;
import hotil.baemo.domains.users.adapter.output.persistence.entity.BaeMoUserEntity;
import hotil.baemo.domains.users.adapter.output.persistence.repository.BaeMoUserJpaRepository;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.community.CommunityImageSupport;
import hotil.baemo.support.domain.community.CommunityLikeSupport;
import hotil.baemo.support.domain.community.CommunitySupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.Random;

import static hotil.baemo.support.util.BaemoTestEnvironment.API_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QueryCommunityApiTest extends ControllerTestBaseSupport {

    @Autowired
    private UserSupport userSupport;
    @Autowired
    private CommunitySupport communitySupport;
    @Autowired
    private CommunityImageSupport communityImageSupport;
    @Autowired
    private CommunityLikeSupport communityLikeSupport;

    @Autowired
    private BaeMoUserJpaRepository baeMoUserJpaRepository;
    @Autowired
    private CommunityJpaRepository communityJpaRepository;
    @Autowired
    private CommunityImageJpaRepository communityImageJpaRepository;
    @Autowired
    private CommunityLikeJpaRepository communityLikeJpaRepository;
    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    private BaeMoUserEntity user;
    private Long communityId;
    private CommunityLikeEntity communityLikeEntity;
    private CommunityImageEntity communityImageEntity;

    @BeforeEach
    void set() {
        clear();
        this.user = userSupport.setUpUser();
        this.communityId = communitySupport.create(user.userId());
        this.communityLikeEntity = communityLikeSupport.save(this.communityId, user.getId());
        this.communityImageEntity = communityImageSupport.create(this.communityId, true);
    }

    @AfterEach
    void clear() {
        baeMoUserJpaRepository.deleteAll();
        communityJpaRepository.deleteAll();
        communityImageJpaRepository.deleteAll();
        communityLikeJpaRepository.deleteAll();
        categoryJpaRepository.deleteAll();
    }

    @RepeatedTest(API_COUNT)
    void 미리_보기_조회에_성공할_것이다() throws Exception {
        final var result = mockMvc.perform(get("/api/communities/list/preview"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        final var saved = communityJpaRepository.loadById(communityId);
        final var imageList = communityImageJpaRepository.findAllByCommunityId(communityId);
        final var writer = baeMoUserJpaRepository.loadById(saved.getWriter());
        final var response = readResult(result, QueryResponse.PreviewList.class);

        assertAll(
            () -> Assertions.assertThat(response.previewList().size()).isEqualTo(1),
            () -> Assertions.assertThat(response.previewList().get(0).communityId()).isEqualTo(saved.getCommunityId()),
            () -> Assertions.assertThat(response.previewList().get(0).category()).isEqualTo(saved.getCommunityCategory()),
            () -> Assertions.assertThat(response.previewList().get(0).title()).isEqualTo(saved.getTitle()),
            () -> Assertions.assertThat(response.previewList().get(0).content()).isEqualTo(saved.getContent()),

            () -> Assertions.assertThat(response.previewList().get(0).thumbnail()).isEqualTo(imageList.get(0).getImage()),

            () -> Assertions.assertThat(response.previewList().get(0).nickname()).isEqualTo(writer.getNickname()),
            () -> Assertions.assertThat(response.previewList().get(0).profileImage()).isEqualTo(writer.getProfileImage()),
            () -> Assertions.assertThat(response.previewList().get(0).realName()).isEqualTo(writer.getRealName()),
            () -> Assertions.assertThat(response.previewList().get(0).writerId()).isEqualTo(writer.getId())
        );
    }

    @RepeatedTest(API_COUNT)
    void 구독한_커뮤니티_미리_보기_조회에_성공할_것이다() throws Exception {
        final var random = new Random();
        communitySupport.create(user.userId(), CommunityCategory.DAILY);
        communitySupport.create(user.userId(), CommunityCategory.EXERCISE_RECRUITMENT);
        communitySupport.create(user.userId(), CommunityCategory.CLUB_PROMOTION);
        communitySupport.create(user.userId(), CommunityCategory.PARTNER_RECRUITMENT);
        communitySupport.create(user.userId(), CommunityCategory.COMPETITION_NOTICE);

        final var savedSubscribe = categoryJpaRepository.save(
            CategoryEntity.builder()
                .userId(user.getId())
                .isDaily(random.nextBoolean())
                .isExerciseRecruitment(random.nextBoolean())
                .isClubsPromotion(random.nextBoolean())
                .isPartnerRecruitment(random.nextBoolean())
                .isCompetitionNotice(random.nextBoolean())
                .build()
        );

        final var result = mockMvc.perform(get("/api/communities/subscribe/list/preview"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        final var response = readResult(result, QueryResponse.PreviewList.class);
        final var resultCategoryList = response.previewList().stream()
            .map(e -> e.category().name())
            .toList();

        final var notSubscribe = !savedSubscribe.getIsClubsPromotion() &&
            !savedSubscribe.getIsDaily() && !savedSubscribe.getIsExerciseRecruitment() &&
            !savedSubscribe.getIsPartnerRecruitment() && !savedSubscribe.getIsCompetitionNotice();

        assertAll(
            () -> {
                if (savedSubscribe.getIsClubsPromotion() || notSubscribe) {
                    Assertions.assertThat(resultCategoryList).contains(CommunityCategory.CLUB_PROMOTION.name());
                } else {
                    Assertions.assertThat(resultCategoryList).doesNotContain(CommunityCategory.CLUB_PROMOTION.name());
                }
            },
            () -> {
                if (savedSubscribe.getIsDaily() || notSubscribe) {
                    Assertions.assertThat(resultCategoryList).contains(CommunityCategory.DAILY.name());
                } else {
                    Assertions.assertThat(resultCategoryList).doesNotContain(CommunityCategory.DAILY.name());
                }
            },
            () -> {
                if (savedSubscribe.getIsExerciseRecruitment() || notSubscribe) {
                    Assertions.assertThat(resultCategoryList).contains(CommunityCategory.EXERCISE_RECRUITMENT.name());
                } else {
                    Assertions.assertThat(resultCategoryList).doesNotContain(CommunityCategory.EXERCISE_RECRUITMENT.name());
                }
            },
            () -> {
                if (savedSubscribe.getIsClubsPromotion() || notSubscribe) {
                    Assertions.assertThat(resultCategoryList).contains(CommunityCategory.CLUB_PROMOTION.name());
                } else {
                    Assertions.assertThat(resultCategoryList).doesNotContain(CommunityCategory.CLUB_PROMOTION.name());
                }
            },
            () -> {
                if (savedSubscribe.getIsPartnerRecruitment() || notSubscribe) {
                    Assertions.assertThat(resultCategoryList).contains(CommunityCategory.PARTNER_RECRUITMENT.name());
                } else {
                    Assertions.assertThat(resultCategoryList).doesNotContain(CommunityCategory.PARTNER_RECRUITMENT.name());
                }
            },
            () -> {
                if (savedSubscribe.getIsCompetitionNotice() || notSubscribe) {
                    Assertions.assertThat(resultCategoryList).contains(CommunityCategory.COMPETITION_NOTICE.name());
                } else {
                    Assertions.assertThat(resultCategoryList).doesNotContain(CommunityCategory.COMPETITION_NOTICE.name());
                }
            }
        );
    }

    @RepeatedTest(API_COUNT)
    void 상세_조회에_성공할_것이다() throws Exception {
        final var result = mockMvc.perform(get("/api/communities/details/{communityId}", communityId))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        final var response = readResult(result, RetrieveCommunity.ReadCommunityDetails.class);
        final var detailsResponse = response.communityDetails();
        final var imageDetailsList = response.imageDetailsList();
        final var saved = communityJpaRepository.loadById(communityId);

        assertAll(
            () -> Assertions.assertThat(detailsResponse.communityId()).isEqualTo(saved.getCommunityId()),
            () -> Assertions.assertThat(detailsResponse.title()).isEqualTo(saved.getTitle()),
            () -> Assertions.assertThat(detailsResponse.content()).isEqualTo(saved.getContent()),
            () -> Assertions.assertThat(detailsResponse.writerId()).isEqualTo(saved.getWriter()),
            () -> Assertions.assertThat(detailsResponse.category()).isEqualTo(saved.getCommunityCategory()),
            () -> Assertions.assertThat(detailsResponse.viewCount()).isEqualTo(saved.getViewCount()),
            () -> Assertions.assertThat(BaeMoTimeUtil.convert(detailsResponse.createdAt())).isEqualTo(saved.getCreatedAt()),
            () -> Assertions.assertThat(BaeMoTimeUtil.convert(detailsResponse.updatedAt())).isEqualTo(saved.getUpdatedAt()),

            () -> Assertions.assertThat(detailsResponse.isLikedByUser()).isEqualTo(communityLikeEntity.getIsLike()),

            () -> Assertions.assertThat(imageDetailsList.list().size()).isEqualTo(1),
            () -> Assertions.assertThat(imageDetailsList.list().get(0).isThumbnail()).isEqualTo(communityImageEntity.getIsThumbnail()),
            () -> Assertions.assertThat(imageDetailsList.list().get(0).imagePath()).isEqualTo(communityImageEntity.getImage())
        );
    }
}