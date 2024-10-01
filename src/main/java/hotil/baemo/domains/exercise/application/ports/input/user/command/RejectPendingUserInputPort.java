package hotil.baemo.domains.exercise.application.ports.input.user.command;

import hotil.baemo.domains.exercise.application.ports.output.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.ExerciseEventOutPort;
import hotil.baemo.domains.exercise.application.usecases.user.command.RejectPendingUserUseCase;
import hotil.baemo.domains.exercise.domain.aggregate.ClubExercise;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.exercise.domain.roles.ExerciseRule;
import hotil.baemo.domains.exercise.domain.roles.RuleSpecification;
import hotil.baemo.domains.exercise.domain.specification.exercise.command.UpdateExerciseUserSpecification;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RejectPendingUserInputPort implements RejectPendingUserUseCase {

    private final CommandExerciseOutputPort commandExerciseOutputPort;
    private final RuleSpecification ruleSpecification;
    private final ExerciseEventOutPort exerciseEventOutPort;

    @Override
    public void rejectPendingMember(ExerciseId exerciseId, UserId userId, UserId targetUserId) {
        Exercise exercise = commandExerciseOutputPort.getExercise(exerciseId);
        ExerciseRule role = ruleSpecification.getRule(exercise.getExerciseType(), exerciseId, userId);

        ExerciseUser rejectedUser = UpdateExerciseUserSpecification.spec(role, exercise).rejectPendingMember(exercise, targetUserId);
        commandExerciseOutputPort.deleteExerciseUser(exerciseId, rejectedUser);
    }

    @Override
    public void rejectPendingGuest(ExerciseId exerciseId, UserId userId, UserId targetUserId) {
        ClubExercise clubExercise = commandExerciseOutputPort.getClubExercise(exerciseId);
        ExerciseRule role = ruleSpecification.getRule(clubExercise.getExerciseType(), exerciseId, userId);

        ExerciseUser rejectedGuest = UpdateExerciseUserSpecification.spec(role, clubExercise).rejectPendingGuest(clubExercise, targetUserId);

        commandExerciseOutputPort.deleteExerciseUser(exerciseId, rejectedGuest);
    }
}
