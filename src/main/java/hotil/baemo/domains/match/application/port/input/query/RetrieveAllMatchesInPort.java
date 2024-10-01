package hotil.baemo.domains.match.application.port.input.query;

import hotil.baemo.domains.match.application.dto.QMatchDTO;
import hotil.baemo.domains.match.application.port.output.QueryMatchOutPort;
import hotil.baemo.domains.match.application.usecase.query.RetrieveAllMatchesUseCase;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.match.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrieveAllMatchesInPort implements RetrieveAllMatchesUseCase {

    private final QueryMatchOutPort queryMatchOutPort;

    @Override
    public List<QMatchDTO.MatchList> retrieveAllMatches(UserId userId, ExerciseId exerciseId) {
        return queryMatchOutPort.retrieveMatchByExercise(userId, exerciseId);
    }
}
