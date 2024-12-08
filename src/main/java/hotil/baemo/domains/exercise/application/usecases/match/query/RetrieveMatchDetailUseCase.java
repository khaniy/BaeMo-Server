package hotil.baemo.domains.exercise.application.usecases.match.query;

import hotil.baemo.domains.exercise.application.dto.QMatchDTO;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface RetrieveMatchDetailUseCase {
    QMatchDTO.MatchDetail retrieveMatchDetail(UserId userId, MatchId matchId);
}
