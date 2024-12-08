package hotil.baemo.domains.community.adapter.output.persistence.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "tb_community_like")
@NoArgsConstructor(access = PROTECTED)
public class CommunityLikeEntity extends BaeMoBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long communityId;
    private Boolean isLike;

    @Builder
    public CommunityLikeEntity(Long id, Long userId, Long communityId, Boolean isLike) {
        this.id = id;
        this.userId = userId;
        this.communityId = communityId;
        this.isLike = isLike != null && isLike;
        BaemoValueObjectValidator.valid(this);
    }

    public void toggle() {
        this.isLike = !this.isLike;
    }
}