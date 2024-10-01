package hotil.baemo.domains.match.domain.aggregate;

import hotil.baemo.core.validator.BaemoValidator;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.match.Order;
import hotil.baemo.domains.match.domain.value.match.Team;
import hotil.baemo.domains.match.domain.value.user.UserId;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
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
