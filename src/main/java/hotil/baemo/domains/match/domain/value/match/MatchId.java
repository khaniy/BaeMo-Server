package hotil.baemo.domains.match.domain.value.match;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record MatchId(
    @Positive
    @NotNull
    Long id
) {
    public MatchId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
