package hotil.baemo.domains.exercise.application.ports.input.match.query;

import hotil.baemo.domains.exercise.application.dto.QMatchDTO;
import hotil.baemo.domains.exercise.application.ports.output.match.QueryMatchOutPort;
import hotil.baemo.domains.exercise.application.usecases.match.query.RetrieveProgressiveMatchesUseCase;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrieveProgressiveMatchesInPort implements RetrieveProgressiveMatchesUseCase {

    private final QueryMatchOutPort queryMatchOutPort;

    @Override
    public List<QMatchDTO.MatchList> retrieveProgressMatches(UserId userId, ExerciseId exerciseId) {
        return queryMatchOutPort.retrieveProgressMatchByExercise(userId, exerciseId);
    }
}
