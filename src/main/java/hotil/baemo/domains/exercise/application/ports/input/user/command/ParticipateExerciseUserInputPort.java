package hotil.baemo.domains.exercise.application.ports.input.user.command;

import hotil.baemo.domains.exercise.application.ports.output.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.ExerciseEventOutPort;
import hotil.baemo.domains.exercise.application.usecases.user.command.ParticipateExerciseUseCase;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.exercise.domain.roles.ExerciseRule;
import hotil.baemo.domains.exercise.domain.roles.RuleSpecification;
import hotil.baemo.domains.exercise.domain.specification.exercise.command.CreateExerciseUserSpecification;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseType;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class ParticipateExerciseUserInputPort implements ParticipateExerciseUseCase {

    private final CommandExerciseOutputPort commandExercisePort;
    private final RuleSpecification ruleSpecification;
    private final ExerciseEventOutPort exerciseEventOutPort;

    @Override
    public void participateExercise(ExerciseId exerciseId, UserId userId) {
        Exercise exercise = commandExercisePort.getExercise(exerciseId);
        ExerciseRule rule = ruleSpecification.getRule(exercise.getExerciseType(), exerciseId, userId);

        ExerciseUser user = CreateExerciseUserSpecification.spec(rule, exercise).participate(userId, exercise);
        commandExercisePort.save(exercise);
        switch (exercise.getExerciseType()) {
            case CLUB -> exerciseEventOutPort.exerciseUserParticipated(exercise, user);
            case IMPROMPTU -> exerciseEventOutPort.exerciseUserApplied(exercise, userId, user);
        }
    }
}