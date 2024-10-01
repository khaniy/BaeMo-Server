package hotil.baemo.domains.clubs.domain.post.value.images;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ClubsPostImageOrder(
    @NotNull
    @Positive
    Long order
) {
    public ClubsPostImageOrder(Long order) {
        this.order = order;
        BaemoValueObjectValidator.valid(this);
    }
}
