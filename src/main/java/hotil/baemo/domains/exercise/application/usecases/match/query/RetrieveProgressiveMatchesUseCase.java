package hotil.baemo.domains.exercise.application.usecases.match.query;

import hotil.baemo.domains.exercise.application.dto.QMatchDTO;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

import java.util.List;

public interface RetrieveProgressiveMatchesUseCase {
    List<QMatchDTO.MatchList> retrieveProgressMatches(UserId userId, ExerciseId exerciseId);
}
