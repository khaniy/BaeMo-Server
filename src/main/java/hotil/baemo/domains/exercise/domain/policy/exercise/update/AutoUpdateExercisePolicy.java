package hotil.baemo.domains.exercise.domain.policy.exercise.update;

import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AutoUpdateExercisePolicy {

    public static LoadStep execute() {
        return LoadStep.of();
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class LoadStep {

        public ExecuteStep get(final Function<ZonedDateTime, List<Exercise>> getExerciseAll) {
            List<Exercise> exerciseList = getExerciseAll.apply(ZonedDateTime.now().minusHours(2L));
            return ExecuteStep.of(exerciseList);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final List<Exercise> exerciseList;

        public PersistStep completeAll() {
            exerciseList.forEach(Exercise::complete);
            return PersistStep.of(exerciseList);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final List<Exercise> exerciseList;

        public EventStep persist(final Consumer<List<Exercise>> repository) {
            repository.accept(exerciseList);
            return EventStep.of(exerciseList.stream()
                .filter(e -> e.getExerciseStatus().equals(ExerciseStatus.COMPLETE))
                .map(Exercise::getExerciseId)
                .collect(Collectors.toList())
            );
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
