package hotil.baemo.domains.match.application.port.output;

import hotil.baemo.domains.match.domain.aggregate.Match;
import hotil.baemo.domains.match.domain.aggregate.MatchOrders;
import hotil.baemo.domains.match.domain.value.club.ClubId;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.user.UserId;

import java.util.List;

public interface CommandMatchOutPort {

    MatchId saveMatch(Match match);

    void saveMatchOrder(MatchOrders exerciseMatchOrders);

    void saveCompletedExerciseMatches(List<Long> exerciseIds);

    Match getMatch(MatchId matchId);

    List<Match> getMatches(List<MatchId> matchIds);

    void deleteMatchUserByExerciseId(ExerciseId exerciseId, UserId userId);

    MatchOrders getMatchOrdersByExerciseId(ExerciseId exerciseId);

    void deleteMatch(Match match);

    List<MatchId> deleteMatchesByExerciseId(ExerciseId exerciseId);

    void deleteMatchUserByClubId(ClubId clubId, UserId userId);
}
