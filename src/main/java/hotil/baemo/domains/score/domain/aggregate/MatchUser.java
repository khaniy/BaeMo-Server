package hotil.baemo.domains.score.domain.aggregate;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.score.Team;
import hotil.baemo.domains.score.domain.value.user.UserId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record MatchUser(
    @NotNull
    MatchId matchId,
    @NotNull
    UserId userId,
    @NotNull
    Team team
) {
    @Builder
    public MatchUser(MatchId matchId, UserId userId, Team team) {
        this.matchId = matchId;
        this.userId = userId;
        this.team = team;
        BaemoValueObjectValidator.valid(this);
    }
}
