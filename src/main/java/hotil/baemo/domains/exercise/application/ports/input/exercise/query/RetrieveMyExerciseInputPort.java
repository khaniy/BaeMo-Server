package hotil.baemo.domains.exercise.application.ports.input.exercise.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseDTO;
import hotil.baemo.domains.exercise.application.ports.output.RetrieveExerciseOutputPort;
import hotil.baemo.domains.exercise.application.usecases.exercise.query.RetrieveMyExercisesUseCase;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RetrieveMyExerciseInputPort implements RetrieveMyExercisesUseCase {

    private final RetrieveExerciseOutputPort retrieveExercisePort;

    @Override
    public List<QExerciseDTO.ExerciseListView> retrieveMyCompleteExercises(UserId userId) {

        return retrieveExercisePort.getUserCompleteExercises(userId);
    }

    @Override
    public QExerciseDTO.MyExercise retrieveMyActivePageExercises(UserId userId) {

        return retrieveExercisePort.getMyActivePageExercises(userId);
    }
}
