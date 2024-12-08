package hotil.baemo.domains.exercise.application.ports.input.court.command;

import hotil.baemo.domains.exercise.application.ports.output.court.CommandExerciseCourtOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.court.LoadExerciseCourtOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.match.LoadMatchOutPort;
import hotil.baemo.domains.exercise.application.ports.output.user.LoadExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.usecases.court.command.DeleteExerciseCourtUseCase;
import hotil.baemo.domains.exercise.domain.policy.court.delete.DeleteExerciseCourtPolicy;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseCourtId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class DeleteExerciseCourtInPort implements DeleteExerciseCourtUseCase {

    private final LoadExerciseUserOutputPort loadExerciseUserOutputPort;
    private final LoadExerciseCourtOutputPort loadExerciseCourtOutputPort;
    private final LoadMatchOutPort loadMatchOutPort;
    private final CommandExerciseCourtOutputPort commandExerciseCourtOutputPort;

    @Override
    public void deleteExerciseCourt(UserId userId, ExerciseId exerciseId, ExerciseCourtId exerciseCourtId) {
        DeleteExerciseCourtPolicy.execute(userId, exerciseId, exerciseCourtId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .load(loadExerciseCourtOutputPort::loadExerciseCourt)
            .check(loadMatchOutPort::existProgressMatch)
            .delete(commandExerciseCourtOutputPort::delete);
    }
}
