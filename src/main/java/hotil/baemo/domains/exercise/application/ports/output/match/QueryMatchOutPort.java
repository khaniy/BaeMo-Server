package hotil.baemo.domains.exercise.application.ports.output.match;

import hotil.baemo.domains.exercise.application.dto.QMatchDTO;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

import java.util.List;

public interface QueryMatchOutPort {

    List<QMatchDTO.MatchList> retrieveMatchByExercise(UserId userId, ExerciseId exerciseId);

    List<QMatchDTO.MatchList> retrieveProgressMatchByExercise(UserId creatorId, ExerciseId exerciseId);

    QMatchDTO.MatchDetail getRetrieveMatchDetail(MatchId matchId);
}
