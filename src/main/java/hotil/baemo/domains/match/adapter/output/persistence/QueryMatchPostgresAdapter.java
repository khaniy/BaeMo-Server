package hotil.baemo.domains.match.adapter.output.persistence;

import hotil.baemo.domains.match.adapter.output.persistence.repository.QueryMatchQRepository;
import hotil.baemo.domains.match.application.dto.QMatchDTO;
import hotil.baemo.domains.match.application.port.output.QueryMatchOutPort;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryMatchPostgresAdapter implements QueryMatchOutPort {

    private final QueryMatchQRepository queryMatchRepository;
    @Override
    public List<QMatchDTO.MatchList> retrieveMatchByExercise(UserId creatorId, ExerciseId exerciseId) {
        return queryMatchRepository.findALLMatchByExercise(exerciseId.id());
    }

    @Override
    public List<QMatchDTO.MatchList> retrieveProgressMatchByExercise(UserId creatorId, ExerciseId exerciseId) {
        return queryMatchRepository.findProgressiveMatchByExercise(exerciseId.id());
    }

    @Override
    public QMatchDTO.MatchDetail getRetrieveMatchDetail(MatchId matchId) {
        return queryMatchRepository.findRetrieveMatchDetail(matchId.id());
    }
}
