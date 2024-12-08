package hotil.baemo.domains.exercise.application.ports.input.exercise.command;

import hotil.baemo.domains.exercise.application.ports.output.exercise.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.exercise.LoadExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.user.LoadExerciseUserOutputPort;
import hotil.baemo.domains.exercise.application.usecases.exercise.command.UpdateExerciseUseCase;
import hotil.baemo.domains.exercise.domain.policy.exercise.update.UpdateClubExercisePolicy;
import hotil.baemo.domains.exercise.domain.policy.exercise.update.UpdateExercisePolicy;
import hotil.baemo.domains.exercise.domain.policy.exercise.update.UpdateThumbnailExercisePolicy;
import hotil.baemo.domains.exercise.domain.value.ClubExerciseVOGroup;
import hotil.baemo.domains.exercise.domain.value.ExerciseVOGroup;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseThumbnail;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateExerciseInPort implements UpdateExerciseUseCase {

    private final CommandExerciseOutputPort commandExerciseOutputPort;
    private final LoadExerciseOutputPort loadExerciseOutputPort;
    private final LoadExerciseUserOutputPort loadExerciseUserOutputPort;

    @Override
    public void updateExercise(UserId userId, ExerciseId exerciseId, ExerciseVOGroup aggregate) {
        UpdateExercisePolicy.execute(userId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .loadExercise(loadExerciseOutputPort::loadExercise)
            .update(aggregate)
            .persist(commandExerciseOutputPort::save);
    }


    @Override
    public void updateClubExercise(UserId userId, ExerciseId exerciseId, ClubExerciseVOGroup aggregate) {
        UpdateClubExercisePolicy.execute(userId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .get(loadExerciseOutputPort::loadClubExercise)
            .update(aggregate)
            .persist(commandExerciseOutputPort::save);
    }

    @Override
    public void updateThumbnail(UserId userId, ExerciseId exerciseId, ExerciseThumbnail thumbnail) {
        UpdateThumbnailExercisePolicy.execute(userId, exerciseId)
            .valid(loadExerciseUserOutputPort::loadExerciseUser)
            .get(loadExerciseOutputPort::loadExercise)
            .updateThumbnail(thumbnail)
            .persist(commandExerciseOutputPort::save);
    }
}
