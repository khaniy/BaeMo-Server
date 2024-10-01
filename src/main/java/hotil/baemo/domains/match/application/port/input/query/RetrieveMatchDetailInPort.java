package hotil.baemo.domains.match.application.port.input.query;

import hotil.baemo.domains.match.application.dto.QMatchDTO;
import hotil.baemo.domains.match.application.port.output.CommandMatchOutPort;
import hotil.baemo.domains.match.application.port.output.QueryMatchOutPort;
import hotil.baemo.domains.match.application.usecase.query.RetrieveMatchDetailUseCase;
import hotil.baemo.domains.match.application.usecase.query.RetrieveProgressiveMatchesUseCase;
import hotil.baemo.domains.match.domain.aggregate.Match;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrieveMatchDetailInPort implements RetrieveMatchDetailUseCase {

    private final QueryMatchOutPort queryMatchOutPort;

    @Override
    public QMatchDTO.MatchDetail retrieveMatchDetail(UserId userId, MatchId matchId) {
        return queryMatchOutPort.getRetrieveMatchDetail(matchId);
    }
}
