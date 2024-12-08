package hotil.baemo.domains.exercise.application.ports.input.user.command;

import hotil.baemo.domains.exercise.application.ports.output.exercise.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.external.QueryClubExternalExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.exercise.LoadExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.user.ExerciseUserEventOutPort;
import hotil.baemo.domains.exercise.application.usecases.user.command.ApplyExerciseGuestUseCase;
import hotil.baemo.domains.exercise.domain.policy.user.create.ApplyGuestPolicy;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class ApplyExerciseGuestInputPort implements ApplyExerciseGuestUseCase {

    private final CommandExerciseOutputPort commandExercisePort;
    private final LoadExerciseOutputPort loadExerciseOutputPort;
    private final ExerciseUserEventOutPort exerciseUserEventOutPort;
    private final QueryClubExternalExerciseOutputPort queryClubExternalExerciseOutputPort;

    @Override
    public void applyExerciseGuest(ExerciseId exerciseId, UserId userId, UserId targetUserId) {
        ApplyGuestPolicy.execute(userId, exerciseId)
            .get(loadExerciseOutputPort::loadClubExercise)
            .valid(queryClubExternalExerciseOutputPort::getMemberFromClub)
            .check(queryClubExternalExerciseOutputPort::existClubMember)
            .applyGuest(userId, targetUserId)
            .persist(commandExercisePort::save)
            .produce(exerciseUserEventOutPort::exerciseUserApplied);
    }
}
