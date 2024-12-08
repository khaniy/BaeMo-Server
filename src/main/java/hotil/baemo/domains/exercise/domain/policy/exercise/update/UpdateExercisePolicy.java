package hotil.baemo.domains.exercise.domain.policy.exercise.update;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.ExerciseVOGroup;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateExercisePolicy {

    private final UserId userId;
    private final ExerciseId exerciseId;


    public static UpdateExercisePolicy execute(UserId userId, ExerciseId exerciseId) {
        return new UpdateExercisePolicy(userId, exerciseId);
    }

    public LoadStep valid(final BiFunction<ExerciseId, UserId, ExerciseUser> getRule) {
        ExerciseUser rule = getRule.apply(exerciseId, userId);
        if (!rule.isAdmin()) {
            throw new CustomException(ResponseCode.EXERCISE_ROLE_AUTH_FAILED);
        }
        return LoadStep.of(exerciseId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class LoadStep {

        private final ExerciseId exerciseId;

        public ExecuteStep loadExercise(final Function<ExerciseId, Exercise> getExercise) {
            Exercise exercise = getExercise.apply(exerciseId);
            return ExecuteStep.of(exercise);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final Exercise exercise;

        public PersistStep update(ExerciseVOGroup aggregate) {
            exercise.update(aggregate);
            return PersistStep.of(exercise);
        }

        public PersistStep updateToProgress() {
            exercise.progress();
            return PersistStep.of(exercise);
        }

        public PersistStep updateToComplete() {
            exercise.complete();
            return PersistStep.of(exercise);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final Exercise exercise;

        public EventStep persist(final Consumer<Exercise> repository) {
            repository.accept(exercise);
            return EventStep.of(List.of(exercise.getExerciseId()));
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class EventStep {

        private final List<ExerciseId> exerciseIdList;

        public void produce(final Consumer<List<ExerciseId>> producer) {
            producer.accept(exerciseIdList);
        }
    }

}
