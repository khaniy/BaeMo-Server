package hotil.baemo.domains.community.adapter.output.persistence.mapper;

import hotil.baemo.domains.community.adapter.output.persistence.entity.CategoryEntity;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.CategoryList;
import hotil.baemo.domains.community.domain.value.CommunityCategory;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

import static hotil.baemo.domains.community.domain.value.CommunityCategory.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class CategoryMapper {

    public static CategoryEntity convert(final CommunityUserId communityUserId, final CategoryList categoryList) {
        return CategoryEntity.builder()
            .userId(communityUserId.id())
            .isDaily(categoryList.contains(DAILY))
            .isClubsPromotion(categoryList.contains(CLUB_PROMOTION))
            .isPartnerRecruitment(categoryList.contains(PARTNER_RECRUITMENT))
            .isExerciseRecruitment(categoryList.contains(EXERCISE_RECRUITMENT))
            .isCompetitionNotice(categoryList.contains(COMPETITION_NOTICE))
            .build();
    }

    public static CategoryList convert(final CategoryEntity categoryEntity) {
        final var list = createConditions(categoryEntity).stream()
            .filter(Map.Entry::getKey)
            .map(Map.Entry::getValue)
            .toList();

        return CategoryList.of(list);
    }

    private static List<Map.Entry<Boolean, CommunityCategory>> createConditions(final CategoryEntity categoryEntity) {
        if (categoryEntity == null) {
            return nullConditions();
        }

        return List.of(
            Map.entry(categoryEntity.getIsDaily() != null && categoryEntity.getIsDaily(), DAILY),
            Map.entry(categoryEntity.getIsExerciseRecruitment() != null && categoryEntity.getIsExerciseRecruitment(), EXERCISE_RECRUITMENT),
            Map.entry(categoryEntity.getIsClubsPromotion() != null && categoryEntity.getIsClubsPromotion(), CLUB_PROMOTION),
            Map.entry(categoryEntity.getIsPartnerRecruitment() != null && categoryEntity.getIsPartnerRecruitment(), PARTNER_RECRUITMENT),
            Map.entry(categoryEntity.getIsCompetitionNotice() != null && categoryEntity.getIsCompetitionNotice(), COMPETITION_NOTICE)
        );
    }

    private static List<Map.Entry<Boolean, CommunityCategory>> nullConditions() {
        return List.of(
            Map.entry(false, DAILY),
            Map.entry(false, EXERCISE_RECRUITMENT),
            Map.entry(false, CLUB_PROMOTION),
            Map.entry(false, PARTNER_RECRUITMENT),
            Map.entry(false, COMPETITION_NOTICE)
        );
    }
}