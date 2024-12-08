package hotil.baemo.domains.exercise.application.ports.output.match;

import hotil.baemo.domains.exercise.domain.entity.court.ExerciseCourt;
import hotil.baemo.domains.exercise.domain.entity.match.Match;
import hotil.baemo.domains.exercise.domain.entity.match.MatchOrders;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

import java.util.List;

public interface LoadMatchOutPort {

    Match loadMatch(MatchId matchId);

    MatchOrders loadMatchOrdersByExerciseId(ExerciseId exerciseId);

    Boolean existProgressMatch(ExerciseCourt exerciseCourt);
}
