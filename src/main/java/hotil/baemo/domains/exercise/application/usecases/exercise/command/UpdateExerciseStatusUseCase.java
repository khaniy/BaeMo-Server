package hotil.baemo.domains.exercise.application.usecases.exercise.command;

import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface UpdateExerciseStatusUseCase {

//    void completeExercisesFromNow();
//
//    void progressExercisesFromNow();

    void completeExercise(UserId userId, ExerciseId exerciseId);

    void progressExercise(UserId userId, ExerciseId exerciseId);

    //    @Override
    //    public void progressExercisesFromNow() {
    //        List<Exercise> exercises = getExerciseOutputPort.getExercisesByStartTime(ZonedDateTime.now());
    //        exercises.forEach(Exercise::progressAuto);
    //        commandExerciseOutputPort.updateExercises(exercises);
    //    }
    //
    void completeExercisesFromNow();
}
