package hotil.baemo.domains.exercise.domain.policy.exercise.delete;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteExercisePolicy {

    private final UserId userId;
    private final ExerciseId exerciseId;


    public static DeleteExercisePolicy execute(UserId userId, ExerciseId exerciseId) {
        return new DeleteExercisePolicy(userId, exerciseId);
    }

    public LoadStep valid(final BiFunction<ExerciseId, UserId, ExerciseUser> getRule) {
        ExerciseUser rule = getRule.apply(exerciseId, userId);
        if (!rule.isAdmin()) {
            throw new CustomException(ResponseCode.EXERCISE_ROLE_AUTH_FAILED);
        }
        return LoadStep.of(exerciseId, userId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class LoadStep {

        private final ExerciseId exerciseId;
        private final UserId userId;

        public ExecuteStep get(final Function<ExerciseId, Exercise> getExercise) {
            Exercise exercise = getExercise.apply(exerciseId);
            return ExecuteStep.of(exercise, userId);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final Exercise exercise;
        private final UserId userId;

        public PersistStep delete() {
            exercise.delete();
            return PersistStep.of(exercise, userId);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final Exercise exercise;
        private final UserId userId;

        public EventStep persist(final Consumer<Exercise> repository) {
            repository.accept(exercise);
            return EventStep.of(exercise, userId);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class EventStep {

        private final Exercise exercise;
        private final UserId userId;

        public void produce(final BiConsumer<Exercise, UserId> producer) {
            producer.accept(exercise, userId);
        }
    }

}
