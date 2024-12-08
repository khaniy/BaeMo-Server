package hotil.baemo.domains.exercise.application.ports.input.user.command;

import hotil.baemo.domains.exercise.application.ports.output.exercise.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.external.QueryClubExternalExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.exercise.LoadExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.user.ExerciseUserEventOutPort;
import hotil.baemo.domains.exercise.application.usecases.user.command.ParticipateExerciseUseCase;
import hotil.baemo.domains.exercise.domain.policy.user.create.ParticipateExercisePolicy;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class ParticipateExerciseUserInputPort implements ParticipateExerciseUseCase {

    private final CommandExerciseOutputPort commandExercisePort;
    private final LoadExerciseOutputPort loadExerciseOutputPort;
    private final QueryClubExternalExerciseOutputPort externalClubOutputPort;
    private final ExerciseUserEventOutPort exerciseUserEventOutPort;

    @Override
    public void participateExercise(ExerciseId exerciseId, UserId userId) {
        ParticipateExercisePolicy.execute(userId, exerciseId)
            .get(loadExerciseOutputPort::loadExercise)
            .valid(externalClubOutputPort::getMemberFromClub)
            .participate(userId)
            .persist(commandExercisePort::save)
            .produce(
                exerciseUserEventOutPort::exerciseUserParticipated,
                exerciseUserEventOutPort::exerciseUserApplied
            );
    }
}