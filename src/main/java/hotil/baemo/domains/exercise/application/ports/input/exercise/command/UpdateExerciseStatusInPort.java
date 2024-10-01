package hotil.baemo.domains.exercise.application.ports.input.exercise.command;

import hotil.baemo.domains.exercise.application.ports.output.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.ExerciseEventOutPort;
import hotil.baemo.domains.exercise.application.usecases.exercise.command.UpdateExerciseStatusUseCase;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.roles.ExerciseRule;
import hotil.baemo.domains.exercise.domain.roles.RuleSpecification;
import hotil.baemo.domains.exercise.domain.specification.exercise.command.UpdateExerciseStatusSpecification;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateExerciseStatusInPort implements UpdateExerciseStatusUseCase {

    private final RuleSpecification ruleSpecification;
    private final ExerciseEventOutPort exerciseEventOutPort;
    private final CommandExerciseOutputPort commandExerciseOutputPort;

    @Override
    public void progressExercise(UserId userId, ExerciseId exerciseId) {
        Exercise exercise = commandExerciseOutputPort.getExercise(exerciseId);
        ExerciseRule role = ruleSpecification.getRule(exercise.getExerciseType(), exerciseId, userId);

        UpdateExerciseStatusSpecification.spec(exercise, role).progressExercise(exercise);
        commandExerciseOutputPort.save(exercise);
    }

    @Override
    public void completeExercise(UserId userId, ExerciseId exerciseId) {
        Exercise exercise = commandExerciseOutputPort.getExercise(exerciseId);
        ExerciseRule role = ruleSpecification.getRule(exercise.getExerciseType(), exerciseId, userId);

        UpdateExerciseStatusSpecification.spec(exercise, role).completeExercise(exercise);

        commandExerciseOutputPort.save(exercise);
        exerciseEventOutPort.exerciseCompleted(new ArrayList<>(List.of(exercise.getExerciseId())));
    }

    @Override
    public void progressExercisesFromNow() {
        List<Exercise> exercises = commandExerciseOutputPort.getExercisesByStartTime(ZonedDateTime.now());
        exercises.forEach(Exercise::progressAuto);
        commandExerciseOutputPort.updateExercises(exercises);
    }

    @Override
    public void completeExercisesFromNow() {
        List<Exercise> exercises = commandExerciseOutputPort.getExercisesByEndTime(ZonedDateTime.now());
        exercises.forEach(Exercise::completeAuto);
        commandExerciseOutputPort.updateExercises(exercises);
        exerciseEventOutPort.exerciseCompleted(exercises.stream()
            .filter(exercise -> exercise.getExerciseStatus().equals(ExerciseStatus.COMPLETE))
            .map(Exercise::getExerciseId)
            .collect(Collectors.toList()));
    }


}
