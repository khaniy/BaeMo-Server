package hotil.baemo.domains.match.application.port.output;

import hotil.baemo.domains.match.application.dto.QMatchDTO;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.user.UserId;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;

import java.util.List;

public interface QueryMatchOutPort {

    List<QMatchDTO.MatchList> retrieveMatchByExercise(UserId creatorId, ExerciseId exerciseId);

    List<QMatchDTO.MatchList> retrieveProgressMatchByExercise(UserId creatorId, ExerciseId exerciseId);

    QMatchDTO.MatchDetail getRetrieveMatchDetail(MatchId matchId);
}
