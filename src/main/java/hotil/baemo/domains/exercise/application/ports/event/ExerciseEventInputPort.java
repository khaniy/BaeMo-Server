package hotil.baemo.domains.exercise.application.ports.event;

import hotil.baemo.domains.exercise.application.ports.output.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.ExerciseEventOutPort;
import hotil.baemo.domains.exercise.application.usecases.event.ExerciseEventUseCase;
import hotil.baemo.domains.exercise.domain.aggregate.ClubExercise;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Service
@RequiredArgsConstructor
public class ExerciseEventInputPort implements ExerciseEventUseCase {

    private final CommandExerciseOutputPort commandExerciseOutputPort;
    private final ExerciseEventOutPort exerciseEventOutPort;

    @Override
    public void userDeleted(UserId userId) {
        List<Exercise> exercises = commandExerciseOutputPort.deleteExerciseUsersByUserId(userId);
        exercises.forEach(
            exercise -> {
                exerciseEventOutPort.exerciseUserCancelled(exercise, userId, true);
            }
        );
    }

    @Override
    public void clubDeleted(ClubId clubId) {
        List<ClubExercise> exercises = commandExerciseOutputPort.deleteClubExercises(clubId);
        exercises.forEach(
            exercise -> {
                exerciseEventOutPort.exerciseDeleted(exercise, new UserId(1L));
            }
        );
    }

    @Override
    public void clubUserCanceled(ClubId clubId, UserId userId) {
        List<ClubExercise> exercises = commandExerciseOutputPort.deleteClubExerciseUsersByUserId(clubId, userId);
        exercises.forEach(
            exercise -> {
                exerciseEventOutPort.exerciseUserCancelled(exercise, userId, false);
            }
        );
    }
}
