package hotil.baemo.domains.clubs.domain.replies.value;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;

public record RepliesLike(
        @NotNull
        Boolean isLike
) {
    public RepliesLike(Boolean isLike) {
        this.isLike = isLike;
        BaemoValueObjectValidator.valid(this);
    }
}