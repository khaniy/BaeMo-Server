package hotil.baemo.domains.clubs.adapter.output.persist.comment.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "tb_club_post_comment_like", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"clubsUserId", "commentId"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubPostCommentLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private Long clubsUserId;
    @NotNull
    @Positive
    private Long commentId;
    @NotNull
    private Boolean isLike;

    @Builder
    public ClubPostCommentLikeEntity(Long id, Long clubsUserId, Long commentId, Boolean isLike) {
        this.id = id;
        this.clubsUserId = clubsUserId;
        this.commentId = commentId;
        this.isLike = isLike;
    }

    public void likeToggle() {
        this.isLike = !this.isLike;
    }
}