package hotil.baemo.domains.exercise.domain.policy.exercise.delete;

import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteAllClubExercisePolicy {

    private final ClubId clubId;


    public static LoadStep execute(ClubId clubId) {
        return LoadStep.of(clubId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class LoadStep {

        private final ClubId clubId;

        public ExecuteStep get(final Function<ClubId, List<Exercise>> getExercise) {
            List<Exercise> exerciseList = getExercise.apply(clubId);
            return ExecuteStep.of(exerciseList);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final List<Exercise> exerciseList;

        public PersistStep deleteAll() {
            exerciseList.forEach(Exercise::delete);
            return PersistStep.of(exerciseList);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final List<Exercise> exerciseList;

        public EventStep persist(final Consumer<List<Exercise>> repository) {
            repository.accept(exerciseList);
            return EventStep.of(exerciseList);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class EventStep {

        private final List<Exercise> exerciseList;

        public void produce(final Consumer<List<ExerciseId>> producer) {
            producer.accept(exerciseList.stream().map(Exercise::getExerciseId).toList());
        }
    }

}
