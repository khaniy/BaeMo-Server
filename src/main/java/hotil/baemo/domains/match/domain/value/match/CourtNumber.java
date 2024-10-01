package hotil.baemo.domains.match.domain.value.match;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CourtNumber(
    @NotNull
    @Positive
    @Max(100)
    Integer number
) {
    public CourtNumber(Integer number) {
        this.number = number;
        BaemoValueObjectValidator.valid(this);
    }
}
