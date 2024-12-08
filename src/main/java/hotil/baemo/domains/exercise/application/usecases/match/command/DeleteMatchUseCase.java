package hotil.baemo.domains.exercise.application.usecases.match.command;

import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface DeleteMatchUseCase {

    void deleteMatch(UserId userId, MatchId matchId);

    void deleteMatchesByExercise(ExerciseId exerciseId);
}
