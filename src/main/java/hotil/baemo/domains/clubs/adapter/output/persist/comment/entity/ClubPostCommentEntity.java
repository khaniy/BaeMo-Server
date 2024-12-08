package hotil.baemo.domains.clubs.adapter.output.persist.comment.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tb_club_post_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubPostCommentEntity extends BaeMoBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Nullable
    private Long preCommentId;
    @Positive
    private Long clubPostId;
    @Positive
    private Long writerId;
    private Long depth;
    @NotBlank
    @Size(max = 1_000)
    private String content;

    @Builder
    public ClubPostCommentEntity(Long id, Long clubPostId, Long writerId,Long depth, Long preCommentId, String content) {
        this.id = id;
        this.clubPostId = clubPostId;
        this.writerId = writerId;
        this.depth = depth;
        this.preCommentId = preCommentId;
        this.content = content;
        BaemoValueObjectValidator.valid(this);
    }
}