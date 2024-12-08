package hotil.baemo.domains.exercise.domain.policy.exercise.create;

import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.value.ExerciseVOGroup;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseThumbnail;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateExercisePolicy {

    public static ExecuteStep execute(UserId userId) {
        return ExecuteStep.of(userId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final UserId userId;

        public PersistStep create(ExerciseVOGroup exerciseVOGroup) {
            final var exercise = Exercise.of(userId, exerciseVOGroup);
            return PersistStep.of(userId, exercise, exerciseVOGroup.thumbnail());
        }
    }


    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final UserId userId;
        private final Exercise exercise;
        private final ExerciseThumbnail exerciseThumbnail;

        public EventStep persist(final BiFunction<Exercise, ExerciseThumbnail, Exercise> repository) {
            Exercise createdExercise = repository.apply(this.exercise, exerciseThumbnail);
            return EventStep.of(userId, createdExercise);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class EventStep {

        private final UserId userId;
        private final Exercise exercise;

        public void produce(final BiConsumer<Exercise, UserId> event) {
            event.accept(exercise, userId);
        }
    }

}
