package hotil.baemo.domains.match.application.usecase.query;

import hotil.baemo.domains.match.application.dto.QMatchDTO;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.user.UserId;

public interface RetrieveMatchDetailUseCase {
    QMatchDTO.MatchDetail retrieveMatchDetail(UserId userId, MatchId matchId);
}
