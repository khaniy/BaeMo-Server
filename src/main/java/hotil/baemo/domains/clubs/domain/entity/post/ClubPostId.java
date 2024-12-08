package hotil.baemo.domains.clubs.domain.entity.post;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ClubPostId(
        @NotNull
        @Positive
        Long id
) {
    public ClubPostId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
