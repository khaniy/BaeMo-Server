package hotil.baemo.domains.notification.domains.value.match;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;

public record MatchCourtNumber(
    @NotNull
    Integer number
) {
    public MatchCourtNumber(Integer number) {
        this.number = number;
        BaemoValueObjectValidator.valid(this);
    }
}
