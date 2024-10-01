package hotil.baemo.domains.match.domain.aggregate;

import hotil.baemo.core.validator.BaemoValidator;
import hotil.baemo.domains.match.domain.value.match.Team;
import hotil.baemo.domains.match.domain.value.user.MatchUserId;
import hotil.baemo.domains.match.domain.value.user.UserId;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MatchUser extends BaemoValidator {
    private final MatchUserId id;
    @NotNull
    private final UserId userId;
    @NotNull
    private final Team team;

    @Builder
    private MatchUser(MatchUserId id, UserId userId, Team team) {
        this.id = id;
        this.userId = userId;
        this.team = team;
        this.valid();
    }
}
