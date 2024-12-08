package hotil.baemo.domains.exercise.domain.entity.match;

import hotil.baemo.core.validator.BaemoValidator;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.match.Order;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MatchOrder extends BaemoValidator {

    @NotNull
    private final MatchId matchId;
    @NotNull
    private final Order order;

    private MatchOrder(MatchId matchId, Order order) {
        this.matchId = matchId;
        this.order = order;
        this.valid();
    }

    public static MatchOrder of(MatchId matchId, Order order) {
        return new MatchOrder(matchId, order);
    }
}
