package hotil.baemo.domains.exercise.domain.entity.match;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.entity.court.ExerciseCourts;
import hotil.baemo.domains.exercise.domain.entity.user.MatchUsers;
import hotil.baemo.domains.exercise.domain.value.exercise.CourtNumber;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.exercise.domain.value.match.Order;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
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

    public static Match init(ExerciseId exerciseId, MatchUsers matchUsers, Order order) {
        return Match.builder()
            .matchStatus(MatchStatus.WAITING)
            .exerciseId(exerciseId)
            .order(order)
            .matchUsers(matchUsers).build();
    }

    public List<UserId> getUserIds() {
        return matchUsers.getUserIds();
    }

    public void updateMatch(MatchUsers matchUsers) {
        this.matchUsers = this.matchUsers.update(matchUsers);
        this.isTeamDefined = this.matchUsers.isTeamDefined();
    }

    public void updateMatch(MatchStatus matchStatus, CourtNumber courtNumber, ExerciseCourts exerciseCourts) {
        this.matchStatus = this.matchStatus.valid(matchStatus);
        if (this.matchStatus.equals(MatchStatus.PROGRESS)) {
            if (!exerciseCourts.has(courtNumber)) {
                throw new CustomException(ResponseCode.EXERCISE_COURT_NOT_FOUND);
            }
            this.courtNumber = courtNumber;
        }else {
            this.courtNumber = null;
        }

    }

    public void nextStatus(CourtNumber courtNumber, ExerciseCourts exerciseCourts) {
        this.matchStatus = this.matchStatus.next();
        if (this.matchStatus.equals(MatchStatus.PROGRESS)) {
            if (!exerciseCourts.has(courtNumber)) {
                throw new CustomException(ResponseCode.EXERCISE_COURT_NOT_FOUND);
            }
            this.courtNumber = courtNumber;
        }
    }

    public void previousStatus() {
        this.matchStatus = this.matchStatus.previous();
        this.courtNumber = null;

    }

    public void delete() {
        this.isDel = true;
    }
}
