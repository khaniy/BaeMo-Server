package hotil.baemo.domains.match.domain.value.match;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TeamScore(

    @NotNull
    @PositiveOrZero
    @Max(31)
    Integer teamAPoint,

    @NotNull
    @PositiveOrZero
    @Max(31)
    Integer teamBPoint
) {
    public TeamScore(Integer teamAPoint, Integer teamBPoint) {
        this.teamAPoint = teamAPoint;
        this.teamBPoint = teamBPoint;
        BaemoValueObjectValidator.valid(this);
    }
}
