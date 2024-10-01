package hotil.baemo.domains.clubs.domain.post.entity;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ClubsPostWriter(
        @NotNull
        @Positive
        Long id
) {
    public ClubsPostWriter(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
