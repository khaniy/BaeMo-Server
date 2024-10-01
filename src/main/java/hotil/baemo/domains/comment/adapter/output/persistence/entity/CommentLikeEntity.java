package hotil.baemo.domains.comment.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "tb_comment_like")
@NoArgsConstructor(access = PROTECTED)
public class CommentLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentLikeId;
    private Long commentId;
    private Long userId;

    private Boolean isLike;

    @Builder
    public CommentLikeEntity(Long commentId, Long userId, Boolean isLike) {
        this.commentId = commentId;
        this.userId = userId;
        this.isLike = isLike != null && isLike;
    }

    public void toggleLike() {
        this.isLike = !this.isLike;
    }
}