package hotil.baemo.domains.comment.adapter.output.persistence.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "tb_comment_like")
@NoArgsConstructor(access = PROTECTED)
public class CommentLikeEntity extends BaeMoBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentLikeId;
    private Long commentId;
    private Long userId;

    @NotNull
    private Boolean isLike;

    @Builder
    public CommentLikeEntity(Long commentId, Long userId, Boolean isLike) {
        this.commentId = commentId;
        this.userId = userId;
        this.isLike = isLike != null && isLike;
        BaemoValueObjectValidator.valid(this);
    }

    public void toggleLike() {
        this.isLike = !this.isLike;
    }
}