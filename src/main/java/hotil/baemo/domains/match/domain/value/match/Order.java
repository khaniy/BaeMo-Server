package hotil.baemo.domains.match.domain.value.match;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Order(
    @NotNull
    @Positive
    Integer order
){
    public Order(Integer order) {
        this.order = order;
        BaemoValueObjectValidator.valid(this);
    }
}
