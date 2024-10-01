package hotil.baemo.domains.score.domain.value.score;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public record TeamPoint(
    @NotNull
    @Min(0)
    @Max(31)
    Integer teamPoint
) {
    public TeamPoint(Integer teamPoint) {
        this.teamPoint = teamPoint;
        BaemoValueObjectValidator.valid(this);
    }

    public static TeamPoint init() {
        return new TeamPoint(0);
    }

    public TeamPoint plus() {
        if (teamPoint >= 31) {
            throw new CustomException(ResponseCode.SCORE_IS_UNDER_31);
        }
        return new TeamPoint(teamPoint + 1);

    }

    public TeamPoint minus() {
        if (teamPoint > 0) {
            return new TeamPoint(teamPoint - 1);
        }
        return this;
    }
}
