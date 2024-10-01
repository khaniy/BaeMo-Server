package hotil.baemo.domains.community.adapter.output.persistence.mapper;

import hotil.baemo.domains.community.adapter.output.persistence.entity.CategoryEntity;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.CategoryList;
import hotil.baemo.domains.community.domain.value.CommunityCategory;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.function.BooleanSupplier;

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
        final Map<BooleanSupplier, CommunityCategory> conditions = createConditions(categoryEntity);

        final var result = conditions.entrySet().stream()
            .filter(e -> e.getKey().getAsBoolean())
            .map(e -> e.getValue().name())
            .toList();

        return CategoryList.getInstanceFromString(result);
    }

    private static Map<BooleanSupplier, CommunityCategory> createConditions(final CategoryEntity categoryEntity) {
        return Map.of(
            categoryEntity::getIsDaily, DAILY,
            categoryEntity::getIsClubsPromotion, CLUB_PROMOTION,
            categoryEntity::getIsPartnerRecruitment, PARTNER_RECRUITMENT,
            categoryEntity::getIsExerciseRecruitment, EXERCISE_RECRUITMENT,
            categoryEntity::getIsCompetitionNotice, COMPETITION_NOTICE
        );
    }
}