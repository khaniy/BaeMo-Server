package hotil.baemo.domains.community.adapter.output.persistence.entity;

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
        this.isDaily = categoryEntity.getIsDaily();
        this.isExerciseRecruitment = categoryEntity.getIsExerciseRecruitment();
        this.isClubsPromotion = categoryEntity.getIsClubsPromotion();
        this.isPartnerRecruitment = categoryEntity.getIsPartnerRecruitment();
        this.isCompetitionNotice = categoryEntity.getIsCompetitionNotice();
    }
}