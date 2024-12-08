package hotil.baemo.domains.exercise.application.ports.input.match.query;

import hotil.baemo.domains.exercise.application.dto.QMatchDTO;
import hotil.baemo.domains.exercise.application.ports.output.match.QueryMatchOutPort;
import hotil.baemo.domains.exercise.application.usecases.match.query.RetrieveMatchDetailUseCase;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveMatchDetailInPort implements RetrieveMatchDetailUseCase {

    private final QueryMatchOutPort queryMatchOutPort;

    @Override
    public QMatchDTO.MatchDetail retrieveMatchDetail(UserId userId, MatchId matchId) {
        return queryMatchOutPort.getRetrieveMatchDetail(matchId);
    }
}
