package hotil.baemo.domains.comment.adapter.output.persistence.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "tb_comment")
@NoArgsConstructor(access = PROTECTED)
public class CommentEntity extends BaeMoBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @NotNull
    @Positive
    private Long communityId;
    @NotNull
    @Positive
    private Long writerId;
    private Long preCommentId;

    @Column(length = 30_000)
    private String content;

    @NotNull
    private Boolean isDelete;

    @Builder
    public CommentEntity(Long communityId, Long writerId, Long preCommentId, String content, Boolean isDelete) {
        this.communityId = communityId;
        this.writerId = writerId;
        this.preCommentId = preCommentId;
        this.content = content;
        this.isDelete = isDelete != null && isDelete;
    }

    public void updateContent(final String newContent) {
        this.content = newContent;
    }

    public void delete() {
        this.isDelete = true;
    }

    public String getContent() {
        if (this.isDelete) {
            return "삭제된 댓글입니다.";
        }

        return this.content;
    }
}