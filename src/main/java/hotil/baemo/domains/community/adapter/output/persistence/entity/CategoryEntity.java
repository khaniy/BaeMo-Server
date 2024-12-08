package hotil.baemo.domains.community.adapter.output.persistence.entity;

import hotil.baemo.domains.community.adapter.output.persistence.mapper.CategoryMapper;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.value.CategoryList;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "tb_community_category")
@NoArgsConstructor(access = PROTECTED)
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Boolean isDaily;
    private Boolean isExerciseRecruitment;
    private Boolean isClubsPromotion;
    private Boolean isPartnerRecruitment;
    private Boolean isCompetitionNotice;

    public static CategoryEntity init(CommunityUserId communityUserId) {
        return CategoryEntity.builder()
            .userId(communityUserId.id())
            .build();
    }

    @Builder
    public CategoryEntity(Long id, Long userId, Boolean isDaily, Boolean isExerciseRecruitment, Boolean isClubsPromotion, Boolean isPartnerRecruitment, Boolean isCompetitionNotice) {
        this.id = id;
        this.userId = userId;
        this.isDaily = isDaily != null && isDaily;
        this.isExerciseRecruitment = isExerciseRecruitment != null && isExerciseRecruitment;
        this.isClubsPromotion = isClubsPromotion != null && isClubsPromotion;
        this.isPartnerRecruitment = isPartnerRecruitment != null && isPartnerRecruitment;
        this.isCompetitionNotice = isCompetitionNotice != null && isCompetitionNotice;
    }

    public void update(CategoryEntity categoryEntity) {
        this.isDaily = categoryEntity.getIsDaily() != null && getIsDaily();
        this.isExerciseRecruitment = categoryEntity.getIsExerciseRecruitment() != null && getIsExerciseRecruitment();
        this.isClubsPromotion = categoryEntity.getIsClubsPromotion() != null && getIsClubsPromotion();
        this.isPartnerRecruitment = categoryEntity.getIsPartnerRecruitment() != null && getIsPartnerRecruitment();
        this.isCompetitionNotice = categoryEntity.getIsCompetitionNotice() != null && getIsCompetitionNotice();
    }

    public CategoryList getCategoryList() {
        return CategoryMapper.convert(this);
    }
}