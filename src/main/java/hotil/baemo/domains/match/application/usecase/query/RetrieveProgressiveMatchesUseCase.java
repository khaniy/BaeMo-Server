package hotil.baemo.domains.match.application.usecase.query;

import hotil.baemo.domains.match.application.dto.QMatchDTO;
import hotil.baemo.domains.match.domain.value.user.UserId;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;

import java.util.List;

public interface RetrieveProgressiveMatchesUseCase {
    List<QMatchDTO.MatchList> retrieveProgressMatches(UserId creatorId, ExerciseId exerciseId);
}
