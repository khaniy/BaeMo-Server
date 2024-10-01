package hotil.baemo.domains.clubs.adapter.post.output.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tb_clubs_post_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubsPostLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Positive
    private Long clubsUserId;
    @NotNull
    @Positive
    private Long clubsPostId;
    @NotNull
    private Boolean isLike;

    @Builder
    public ClubsPostLikeEntity(Long id, Long clubsUserId, Long clubsPostId, Boolean isLike) {
        this.id = id;
        this.clubsUserId = clubsUserId;
        this.clubsPostId = clubsPostId;
        this.isLike = isLike == null || isLike;
    }

    public void likeToggle() {
        this.isLike = !this.isLike;
    }
}