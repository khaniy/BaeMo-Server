package hotil.baemo.domains.exercise.application.ports.input.exercise.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseDTO;
import hotil.baemo.domains.exercise.application.ports.output.RetrieveExerciseOutputPort;
import hotil.baemo.domains.exercise.application.usecases.exercise.query.RetrieveMainPageExercisesUseCase;
import hotil.baemo.domains.exercise.domain.roles.RuleSpecification;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrieveMainPageExerciseInputPort implements RetrieveMainPageExercisesUseCase {

    private final RetrieveExerciseOutputPort retrieveExercisePort;


    @Override
    public List<QExerciseDTO.ExerciseListView> retrieveMainExercises(UserId userId) {
        return retrieveExercisePort.getMainPageExercises();
    }
}