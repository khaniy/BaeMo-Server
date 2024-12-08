package hotil.baemo.domains.exercise.application.ports.input.court.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseCourtDTO;
import hotil.baemo.domains.exercise.application.ports.output.court.RetrieveExerciseCourtOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.user.LoadExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.usecases.court.query.RetrieveExerciseCourtUseCase;
import hotil.baemo.domains.exercise.domain.policy.court.retrieve.RetrieveExerciseCourtPolicy;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RetrieveExerciseCourtInPort implements RetrieveExerciseCourtUseCase {

    private final LoadExerciseUserOutputPort loadExerciseUserOutputPort;
    private final RetrieveExerciseCourtOutputPort retrieveExerciseCourtOutputPort;

    @Override
    public List<QExerciseCourtDTO.ExerciseCourt> retrieveExerciseCourt(UserId userId, ExerciseId exerciseId) {
        return RetrieveExerciseCourtPolicy.execute(userId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .load(retrieveExerciseCourtOutputPort::getExerciseCourts);
    }
}
