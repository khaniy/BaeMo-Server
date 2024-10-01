package hotil.baemo.domains.exercise.application.ports.input.exercise.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseDTO;
import hotil.baemo.domains.exercise.application.ports.output.RetrieveExerciseOutputPort;
import hotil.baemo.domains.exercise.application.usecases.exercise.query.RetrieveUserExercisesUseCase;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RetrieveUserExerciseInputPort implements RetrieveUserExercisesUseCase {

    private final RetrieveExerciseOutputPort retrieveExercisePort;

    @Override
    public List<QExerciseDTO.ExerciseListView> retrieveUserExercises(UserId userId) {
        return retrieveExercisePort.getUserExercises(userId);
    }
}
