package hotil.baemo.domains.notification.domains.value.match;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MatchId(
    @NotNull
    @Positive
    Long id
) {
    public MatchId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
