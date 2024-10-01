package hotil.baemo.domains.exercise.application.ports.input.user.command;

import hotil.baemo.domains.exercise.application.ports.output.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.ExerciseEventOutPort;
import hotil.baemo.domains.exercise.application.ports.output.ExerciseExternalClubOutputPort;
import hotil.baemo.domains.exercise.application.usecases.user.command.ApplyExerciseGuestUseCase;
import hotil.baemo.domains.exercise.domain.aggregate.ClubExercise;
import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.exercise.domain.roles.ExerciseRule;
import hotil.baemo.domains.exercise.domain.roles.RuleSpecification;
import hotil.baemo.domains.exercise.domain.specification.exercise.command.CreateExerciseUserSpecification;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ApplyExerciseGuestInputPort implements ApplyExerciseGuestUseCase {

    private final CommandExerciseOutputPort commandExercisePort;
    private final ExerciseEventOutPort exerciseEventOutPort;
    private final ExerciseExternalClubOutputPort exerciseExternalClubOutputPort;
    private final RuleSpecification ruleSpecification;

    @Override
    public void applyExerciseGuest(ExerciseId exerciseId, UserId userId, UserId targetUserId) {
        ClubExercise clubExercise = commandExercisePort.getClubExercise(exerciseId);
        List<UserId> clubUserIds = exerciseExternalClubOutputPort.getClubUserIds(clubExercise.getClubId());
        ExerciseRule rule = ruleSpecification.getRule(clubExercise.getExerciseType(), exerciseId, userId);

        ExerciseUser targetUser = CreateExerciseUserSpecification.spec(rule, clubExercise).applyGuest(userId, clubExercise, targetUserId, clubUserIds);
        commandExercisePort.save(clubExercise);
        exerciseEventOutPort.exerciseUserApplied(clubExercise, userId, targetUser);
    }
}
