package hotil.baemo.domains.exercise.application.ports.input.exercise.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseDTO;
import hotil.baemo.domains.exercise.application.ports.output.exercise.LoadExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.exercise.RetrieveExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.user.LoadExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.usecases.exercise.query.RetrieveExerciseDetailsUseCase;
import hotil.baemo.domains.exercise.domain.policy.exercise.retrieve.RetrieveExerciseDetailsPolicy;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveExerciseDetailsInputPort implements RetrieveExerciseDetailsUseCase {

    private final RetrieveExerciseOutputPort retrieveExercisePort;
    private final LoadExerciseUserOutputPort loadExerciseUserOutputPort;

    @Override
    public QExerciseDTO.ExerciseDetailViewWithAuth retrieveDetails(UserId userId, ExerciseId exerciseId) {
        return RetrieveExerciseDetailsPolicy.execute(userId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .get(retrieveExercisePort::getExerciseDetail);
    }
}