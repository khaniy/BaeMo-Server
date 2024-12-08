package hotil.baemo.domains.clubs.domain.entity.comment;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ClubPostCommentId(
        @NotNull
        @Positive
        Long id
) {
    public ClubPostCommentId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
