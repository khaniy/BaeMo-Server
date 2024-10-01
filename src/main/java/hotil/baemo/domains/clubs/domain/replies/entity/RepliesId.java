package hotil.baemo.domains.clubs.domain.replies.entity;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RepliesId(
        @NotNull
        @Positive
        Long id
) {
    public RepliesId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
