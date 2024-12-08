package hotil.baemo.domains.exercise.application.ports.input.court.command;

import hotil.baemo.domains.exercise.application.ports.output.court.CommandExerciseCourtOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.court.LoadExerciseCourtOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.user.LoadExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.usecases.court.command.CreateExerciseCourtUseCase;
import hotil.baemo.domains.exercise.domain.policy.court.create.CreateExerciseCourtPolicy;
import hotil.baemo.domains.exercise.domain.value.exercise.CourtNumber;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CreateExerciseCourtInPort implements CreateExerciseCourtUseCase {

    private final LoadExerciseUserOutputPort loadExerciseUserOutputPort;
    private final LoadExerciseCourtOutputPort loadExerciseCourtOutputPort;
    private final CommandExerciseCourtOutputPort commandExerciseCourtOutputPort;

    @Override
    public synchronized void createExerciseCourt(UserId userId, ExerciseId exerciseId, CourtNumber courtNumber) {
        CreateExerciseCourtPolicy.execute(userId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .load(loadExerciseCourtOutputPort::loadExerciseCourts)
            .create(courtNumber)
            .persist(commandExerciseCourtOutputPort::save);
    }
}
