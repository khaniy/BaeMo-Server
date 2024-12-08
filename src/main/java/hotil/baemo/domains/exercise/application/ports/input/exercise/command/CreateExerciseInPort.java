package hotil.baemo.domains.exercise.application.ports.input.exercise.command;

import hotil.baemo.domains.exercise.application.ports.output.exercise.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.exercise.ExerciseEventOutPort;
import hotil.baemo.domains.exercise.application.ports.output.external.QueryClubExternalExerciseOutputPort;
import hotil.baemo.domains.exercise.application.usecases.exercise.command.CreateExerciseUseCase;
import hotil.baemo.domains.exercise.domain.policy.exercise.create.CreateClubExercisePolicy;
import hotil.baemo.domains.exercise.domain.policy.exercise.create.CreateExercisePolicy;
import hotil.baemo.domains.exercise.domain.value.ClubExerciseVOGroup;
import hotil.baemo.domains.exercise.domain.value.ExerciseVOGroup;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateExerciseInPort implements CreateExerciseUseCase {

    private final CommandExerciseOutputPort commandExerciseOutputPort;
    private final QueryClubExternalExerciseOutputPort queryClubExternalExerciseOutputPort;
    private final ExerciseEventOutPort exerciseEventOutPort;

    @Override
    public void createExercise(UserId userId, ExerciseVOGroup aggregate) {
        CreateExercisePolicy.execute(userId)
            .create(aggregate)
            .persist(commandExerciseOutputPort::save)
            .produce(exerciseEventOutPort::exerciseCreated);
    }

    @Override
    public void createClubExercise(UserId userId, ClubId clubId, ClubExerciseVOGroup aggregate) {
        CreateClubExercisePolicy.execute(userId, clubId)
            .valid(queryClubExternalExerciseOutputPort::getMemberFromClub)
            .create(aggregate)
            .persist(commandExerciseOutputPort::save)
            .produce(exerciseEventOutPort::exerciseCreated);
    }
}
