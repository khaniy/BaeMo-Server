package hotil.baemo.domains.exercise.application.ports.input.exercise.command;

import hotil.baemo.domains.exercise.application.ports.output.exercise.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.exercise.ExerciseEventOutPort;
import hotil.baemo.domains.exercise.application.ports.output.exercise.LoadExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.user.LoadExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.usecases.exercise.command.UpdateExerciseStatusUseCase;
import hotil.baemo.domains.exercise.domain.policy.exercise.update.AutoUpdateExercisePolicy;
import hotil.baemo.domains.exercise.domain.policy.exercise.update.UpdateExercisePolicy;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateExerciseStatusInPort implements UpdateExerciseStatusUseCase {

    private final ExerciseEventOutPort exerciseEventOutPort;
    private final LoadExerciseUserOutputPort loadExerciseUserOutputPort;
    private final CommandExerciseOutputPort commandExerciseOutputPort;
    private final LoadExerciseOutputPort loadExerciseOutputPort;

    @Override
    public void progressExercise(UserId userId, ExerciseId exerciseId) {
        UpdateExercisePolicy.execute(userId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .loadExercise(loadExerciseOutputPort::loadExercise)
            .updateToProgress()
            .persist(commandExerciseOutputPort::save);
    }

    @Override
    public void completeExercise(UserId userId, ExerciseId exerciseId) {
        UpdateExercisePolicy.execute(userId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .loadExercise(loadExerciseOutputPort::loadExercise)
            .updateToComplete()
            .persist(commandExerciseOutputPort::save)
            .produce(exerciseEventOutPort::exerciseCompleted);
    }

    @Override
    public void completeExercisesFromNow() {
        AutoUpdateExercisePolicy.execute()
            .get(loadExerciseOutputPort::loadExerciseByEndTime)
            .completeAll()
            .persist(commandExerciseOutputPort::saveAll)
            .produce(exerciseEventOutPort::exerciseCompleted);
    }
}
//    @Override
//    public void progressExercisesFromNow() {
//        List<Exercise> exercises = getExerciseOutputPort.getExercisesByStartTime(ZonedDateTime.now());
//        exercises.forEach(Exercise::progressAuto);
//        commandExerciseOutputPort.updateExercises(exercises);
//    }
//
