package hotil.baemo.domains.clubs.domain.replies.entity;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.annotation.Nullable;

public record PreRepliesId(
    @Nullable
    Long id
) {
    public PreRepliesId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
