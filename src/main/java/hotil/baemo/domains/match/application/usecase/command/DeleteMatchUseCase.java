package hotil.baemo.domains.match.application.usecase.command;

import hotil.baemo.domains.match.domain.value.club.ClubId;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.user.UserId;

public interface DeleteMatchUseCase {

    void deleteMatch(UserId modifierId, MatchId matchId);

    void deleteMatchesByExercise(ExerciseId exerciseId);

    void deleteMatchUsersByExerciseUser(ExerciseId exerciseId, UserId userId);

    void deleteMatchUsersByClubUser(ClubId clubId, UserId userId);
}
