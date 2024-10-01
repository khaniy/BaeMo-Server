package hotil.baemo.domains.clubs.adapter.replies.output.persistence.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "tb_replies_stat", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"clubsUserId", "repliesId"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StatRepliesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private Long clubsUserId;
    @NotNull
    @Positive
    private Long repliesId;
    @NotNull
    private Boolean isLike;

    @Builder
    public StatRepliesEntity(Long id, Long clubsUserId, Long repliesId, Boolean isLike) {
        this.id = id;
        this.clubsUserId = clubsUserId;
        this.repliesId = repliesId;
        this.isLike = isLike;
    }

    public void likeToggle() {
        this.isLike = !this.isLike;
    }
}