package hotil.baemo.domains.clubs.adapter.replies.output.persistence.entity;

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
@Table(name = "tb_replies")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RepliesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repliesId;
    @Positive
    private Long repliesPostId;
    @Positive
    private Long repliesWriter;
    @Nullable
    private Long preRepliesId;
    @NotBlank
    @Size(max = 1_000)
    private String repliesContent;

    @Builder
    public RepliesEntity(Long repliesId, Long repliesPostId, Long repliesWriter, Long preRepliesId, String repliesContent) {
        this.repliesId = repliesId;
        this.repliesPostId = repliesPostId;
        this.repliesWriter = repliesWriter;
        this.preRepliesId = preRepliesId;
        this.repliesContent = repliesContent;
        BaemoValueObjectValidator.valid(this);
    }
}