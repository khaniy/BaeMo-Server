package hotil.baemo.domains.exercise.application.ports.output.match;

import hotil.baemo.domains.exercise.domain.entity.match.Match;
import hotil.baemo.domains.exercise.domain.entity.match.MatchOrders;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface CommandMatchOutPort {

    MatchId saveMatch(Match match);

    void saveMatchOrder(MatchOrders exerciseMatchOrders);

    void deleteMatchUserByExerciseId(ExerciseId exerciseId, UserId userId);

    void deleteMatch(Match match);

    void deleteMatchesByExerciseId(ExerciseId exerciseId);
}
