package hotil.baemo.domains.exercise.application.ports.input.court.command;

import hotil.baemo.domains.exercise.application.ports.output.court.CommandExerciseCourtOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.court.LoadExerciseCourtOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.user.LoadExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.usecases.court.command.UpdateExerciseCourtUseCase;
import hotil.baemo.domains.exercise.domain.policy.court.update.UpdateExerciseCourtPolicy;
import hotil.baemo.domains.exercise.domain.value.exercise.CourtNumber;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseCourtId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateExerciseCourtInPort implements UpdateExerciseCourtUseCase {

    private final LoadExerciseUserOutputPort loadExerciseUserOutputPort;
    private final LoadExerciseCourtOutputPort loadExerciseCourtOutputPort;
    private final CommandExerciseCourtOutputPort commandExerciseCourtOutputPort;

    @Override
    public void updateExerciseCourt(UserId userId, ExerciseId exerciseId, ExerciseCourtId exerciseCourtId, CourtNumber courtNumber) {
//        UpdateExerciseCourtPolicy.execute(userId, exerciseId, exerciseCourtId)
//            .valid(loadExerciseUserOutputPort::loadExerciseUser)
//            .load(loadExerciseCourtOutputPort::loadExerciseCourt)
//            .update(courtNumber)
//            .persist(commandExerciseCourtOutputPort::save);
    }

}
