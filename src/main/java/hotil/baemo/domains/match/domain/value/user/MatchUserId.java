package hotil.baemo.domains.match.domain.value.user;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record MatchUserId(
    @Positive
    @NotNull
    Long id
) {
    public MatchUserId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
