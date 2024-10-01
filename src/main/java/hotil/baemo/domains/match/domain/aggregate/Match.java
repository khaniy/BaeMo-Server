package hotil.baemo.domains.match.domain.aggregate;

import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.match.domain.value.match.CourtNumber;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.match.MatchStatus;
import hotil.baemo.domains.match.domain.value.match.Order;
import hotil.baemo.domains.match.domain.value.user.UserId;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Match {

    private final MatchId matchId;
    private final ExerciseId exerciseId;
    private MatchUsers matchUsers;
    private CourtNumber courtNumber;
    private Order order;
    private MatchStatus matchStatus;
    private boolean isDel = false;
    private boolean isTeamDefined;

    @Builder
    private Match(MatchId matchId, ExerciseId exerciseId, MatchUsers matchUsers, MatchStatus matchStatus, CourtNumber courtNumber, Order order) {
        this.matchId = matchId;
        this.matchUsers = matchUsers;
        this.exerciseId = exerciseId;
        this.matchStatus = matchStatus;
        this.courtNumber = courtNumber;
        this.order = order;
        this.isTeamDefined = matchUsers.isTeamDefined();
    }

    public static Match init(ExerciseId exerciseId, CourtNumber courtNumber, MatchUsers matchUsers, Order order) {
        return Match.builder()
            .matchStatus(MatchStatus.WAITING)
            .exerciseId(exerciseId)
            .courtNumber(courtNumber)
            .order(order)
            .matchUsers(matchUsers).build();
    }

    public List<UserId> getUserIds() {
        return matchUsers.getUserIds();
    }

    public void updateMatch(MatchUsers matchUsers, CourtNumber courtNumber) {
        this.matchUsers = matchUsers;
        this.courtNumber = courtNumber;
        this.isTeamDefined = matchUsers.isTeamDefined();
    }

    public void nextStatus() {
        this.matchStatus = this.matchStatus.next();
    }

    public void previousStatus() {
        this.matchStatus = this.matchStatus.previous();
    }

    public void delete() {
        this.isDel = true;
    }
}
