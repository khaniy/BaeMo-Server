package hotil.baemo.domains.score.domain.value.score;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ScoreId(
    @NotNull
    @Positive
    Long id
) {
    public ScoreId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
