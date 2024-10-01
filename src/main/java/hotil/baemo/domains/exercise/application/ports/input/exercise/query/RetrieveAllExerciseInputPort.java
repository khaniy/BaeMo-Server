package hotil.baemo.domains.exercise.application.ports.input.exercise.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseDTO;
import hotil.baemo.domains.exercise.application.ports.output.RetrieveExerciseOutputPort;
import hotil.baemo.domains.exercise.application.usecases.exercise.query.RetrieveAllExercisesUseCase;
import hotil.baemo.domains.exercise.application.usecases.exercise.query.RetrieveMainPageExercisesUseCase;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrieveAllExerciseInputPort implements RetrieveAllExercisesUseCase {

    private final RetrieveExerciseOutputPort retrieveExercisePort;

    @Override
    public List<QExerciseDTO.ExerciseListView> retrieveAllExercises(UserId userId, Pageable pageable) {
        return retrieveExercisePort.getAllExercises(pageable);
    }
}