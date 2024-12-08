package hotil.baemo.domains.exercise.domain.policy.user.delete;

import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ExpelAllExercisesPolicy {

    public static LoadStep execute(UserId userId) {
        return new LoadStep(userId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class LoadStep {

        private final UserId userId;

        public ExecuteStep get(final Function<UserId, List<Exercise>> getExercise) {
            List<Exercise> exerciseList = getExercise.apply(userId);
            return ExecuteStep.of(userId, exerciseList);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final UserId userId;
        private final List<Exercise> exerciseList;

        public PersistStep expelMember(UserId memberId) {
            Map<Exercise, ExerciseUser> results = exerciseList.stream()
                .collect(Collectors.toMap(
                    exercise -> exercise,
                    exercise -> exercise.expelMember(memberId)
                ));
            return PersistStep.of(userId, results);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final UserId userId;
        private final Map<Exercise, ExerciseUser> exerciseMap;

        public PersistStep deleteAllExerciseUser(
            final Consumer<List<ExerciseUser>> deleteAll
        ) {
            deleteAll.accept(new ArrayList<>(exerciseMap.values()));
            return this;
        }

        public PersistStep saveAllExercise(
            final Consumer<List<Exercise>> saveAll
        ) {
            saveAll.accept(new ArrayList<>(exerciseMap.keySet()));
            return this;
        }

        public EventStep deleteAllMatchUser(
            final BiConsumer<ExerciseId, UserId> deleteAll
        ) {
            exerciseMap.keySet().forEach(exercise -> {
                deleteAll.accept(exercise.getExerciseId(), userId);
            });
            return EventStep.of(exerciseMap);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class EventStep {

        private final Map<Exercise, ExerciseUser> exerciseMap;

        public void produce(
            final BiConsumer<Exercise, ExerciseUser> producer
        ) {
            exerciseMap.forEach(producer::accept);
        }
    }
}
