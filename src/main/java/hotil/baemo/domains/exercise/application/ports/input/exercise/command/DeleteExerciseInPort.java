package hotil.baemo.domains.exercise.application.ports.input.exercise.command;

import hotil.baemo.domains.exercise.application.ports.output.exercise.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.exercise.ExerciseEventOutPort;
import hotil.baemo.domains.exercise.application.ports.output.exercise.LoadExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.user.LoadExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.usecases.exercise.command.DeleteExerciseUseCase;
import hotil.baemo.domains.exercise.domain.policy.exercise.delete.DeleteAllClubExercisePolicy;
import hotil.baemo.domains.exercise.domain.policy.exercise.delete.DeleteExercisePolicy;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
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
    private final LoadExerciseUserOutputPort loadExerciseUserOutputPort;
    private final LoadExerciseOutputPort loadExerciseOutputPort;
    private final ExerciseEventOutPort exerciseEventOutPort;

    @Override
    public void deleteExercise(ExerciseId exerciseId, UserId userId) {
        DeleteExercisePolicy.execute(userId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .get(loadExerciseOutputPort::loadExercise)
            .delete()
            .persist(commandExerciseOutputPort::delete)
            .produce(exerciseEventOutPort::exerciseDeleted);
    }

    @Override
    public void deleteAllActiveClubExercises(ClubId clubId) {
        DeleteAllClubExercisePolicy.execute(clubId)
            .get(loadExerciseOutputPort::loadAllActiveClubExercises)
            .deleteAll()
            .persist(commandExerciseOutputPort::deleteAll)
            .produce(exerciseEventOutPort::exerciseDeleted);
    }
}
