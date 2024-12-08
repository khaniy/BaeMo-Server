package hotil.baemo.domains.clubs.domain.value.post.images;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ClubPostImageOrder(
    @NotNull
    @Positive
    Long order
) {
    public ClubPostImageOrder(Long order) {
        this.order = order;
        BaemoValueObjectValidator.valid(this);
    }
}
