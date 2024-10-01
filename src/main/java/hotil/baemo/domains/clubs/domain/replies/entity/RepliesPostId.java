package hotil.baemo.domains.clubs.domain.replies.entity;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RepliesPostId(
        @NotNull
        @Positive
        Long id
) {
    public RepliesPostId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
