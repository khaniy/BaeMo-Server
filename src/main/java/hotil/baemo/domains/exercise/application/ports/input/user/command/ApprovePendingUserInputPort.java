package hotil.baemo.domains.exercise.application.ports.input.user.command;

import hotil.baemo.domains.exercise.application.ports.output.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.ExerciseEventOutPort;
import hotil.baemo.domains.exercise.application.usecases.user.command.ApprovePendingUserUseCase;
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
public class ApprovePendingUserInputPort implements ApprovePendingUserUseCase {

    private final CommandExerciseOutputPort commandExerciseOutputPort;
    private final RuleSpecification ruleSpecification;
    private final ExerciseEventOutPort exerciseEventOutPort;

    @Override
    public void approvePendingMember(ExerciseId exerciseId, UserId userId, UserId targetUserId) {
        Exercise exercise = commandExerciseOutputPort.getExercise(exerciseId);
        ExerciseRule role = ruleSpecification.getRule(exercise.getExerciseType(), exerciseId, userId);

        ExerciseUser targetUser = UpdateExerciseUserSpecification.spec(role, exercise).approvePendingMember(exercise, targetUserId);
        commandExerciseOutputPort.save(exercise);
        exerciseEventOutPort.exerciseUserApproved(exercise, targetUser);
        exerciseEventOutPort.exerciseUserParticipated(exercise, targetUser);

    }

    @Override
    public void approvePendingGuest(ExerciseId exerciseId, UserId userId, UserId targetUserId) {
        ClubExercise clubExercise = commandExerciseOutputPort.getClubExercise(exerciseId);
        ExerciseRule role = ruleSpecification.getRule(clubExercise.getExerciseType(), exerciseId, userId);

        ExerciseUser targetUser = UpdateExerciseUserSpecification.spec(role, clubExercise).approvePendingGuest(clubExercise, targetUserId);

        commandExerciseOutputPort.save(clubExercise);
        exerciseEventOutPort.exerciseUserApproved(clubExercise, targetUser);
        exerciseEventOutPort.exerciseUserParticipated(clubExercise, targetUser);

    }
}
