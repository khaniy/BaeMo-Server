package hotil.baemo.domains.exercise.application.ports.input.exercise.command;

import hotil.baemo.domains.exercise.application.ports.output.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.ExerciseEventOutPort;
import hotil.baemo.domains.exercise.application.usecases.exercise.command.DeleteExerciseUseCase;
import hotil.baemo.domains.exercise.domain.roles.ExerciseRule;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.roles.RuleSpecification;
import hotil.baemo.domains.exercise.domain.specification.exercise.command.DeleteExerciseSpecification;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class DeleteExerciseInPort implements DeleteExerciseUseCase {

    private final CommandExerciseOutputPort commandExerciseOutputPort;
    private final ExerciseEventOutPort exerciseEventOutPort;
    private final RuleSpecification ruleSpecification;

    @Override
    public void deleteExercise(ExerciseId exerciseId, UserId userId) {
        Exercise exercise = commandExerciseOutputPort.getExercise(exerciseId);
        ExerciseRule role = ruleSpecification.getRule(exercise.getExerciseType(), exerciseId, userId);

        DeleteExerciseSpecification.spec(role, exercise).delete(exercise);
        commandExerciseOutputPort.deleteExercise(exercise);
        exerciseEventOutPort.exerciseDeleted(exercise, userId);
    }
}
