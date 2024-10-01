package hotil.baemo.domains.exercise.application.ports.input.exercise.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseDTO;
import hotil.baemo.domains.exercise.application.dto.ExerciseDetailViewAuth;
import hotil.baemo.domains.exercise.application.ports.output.RetrieveExerciseOutputPort;
import hotil.baemo.domains.exercise.application.usecases.exercise.query.RetrieveExerciseDetailsUseCase;
import hotil.baemo.domains.exercise.domain.roles.RuleSpecification;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveExerciseDetailsInputPort implements RetrieveExerciseDetailsUseCase {

    private final RetrieveExerciseOutputPort retrieveExercisePort;
    private final RuleSpecification ruleSpecification;

    @Override
    public QExerciseDTO.ExerciseDetailViewWithAuth retrieveDetails(UserId userId, ExerciseId exerciseId) {

        var exerciseDetail = retrieveExercisePort.getExerciseDetail(exerciseId);
        var rule = ruleSpecification.getExerciseDetailViewAuth(exerciseId, userId);
        return new QExerciseDTO.ExerciseDetailViewWithAuth(exerciseDetail, rule);
    }
}