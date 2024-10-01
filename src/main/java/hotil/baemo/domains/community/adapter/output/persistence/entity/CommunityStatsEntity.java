package hotil.baemo.domains.community.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "tb_community_stats")
@NoArgsConstructor(access = PROTECTED)
public class CommunityStatsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityStatsId;

    private Long userId;
    private Long communityId;
    private Boolean isLike;
    private Integer viewCount;

    @Builder
    public CommunityStatsEntity(Long userId, Long communityId, Boolean isLike, Integer viewCount) {
        this.userId = userId;
        this.communityId = communityId;
        this.isLike = isLike != null && isLike;
        this.viewCount = viewCount == null ? 0 : viewCount;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void likeToggle() {
        this.isLike = !this.isLike;
    }
}