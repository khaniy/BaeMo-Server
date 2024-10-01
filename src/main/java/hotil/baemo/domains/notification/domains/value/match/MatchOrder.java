package hotil.baemo.domains.notification.domains.value.match;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;

public record MatchOrder(
    @NotNull
    Integer order
) {
    public MatchOrder(Integer order) {
        this.order = order;
        BaemoValueObjectValidator.valid(this);
    }
}
