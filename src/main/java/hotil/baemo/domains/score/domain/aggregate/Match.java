package hotil.baemo.domains.score.domain.aggregate;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.match.MatchStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record Match(
    @NotNull
    MatchId id,
    @NotNull
    MatchStatus status,
    @NotNull
    boolean isTeamDefined
) {


    @Builder
    public Match(MatchId id, MatchStatus status, boolean isTeamDefined) {
        this.id = id;
        this.status = status;
        this.isTeamDefined = isTeamDefined;
        BaemoValueObjectValidator.valid(this);
    }
}
