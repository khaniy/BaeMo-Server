package hotil.baemo.domains.exercise.domain.policy.user.delete;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.entity.exercise.ClubExercise;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LeaveExercisePolicy {

    private final UserId userId;
    private final ExerciseId exerciseId;


    public static LeaveExercisePolicy execute(UserId userId, ExerciseId exerciseId) {
        return new LeaveExercisePolicy(userId, exerciseId);
    }

    public LoadStep valid(final BiFunction<ExerciseId, UserId, ExerciseUser> getRule) {
        ExerciseUser rule = getRule.apply(exerciseId, userId);
        return LoadStep.of(exerciseId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class LoadStep {

        private final ExerciseId exerciseId;
        private static final EnumSet<ExerciseStatus> STATUS_SET = EnumSet.of(
            ExerciseStatus.PROGRESS,
            ExerciseStatus.COMPLETE
        );

        public ExecuteStep get(final Function<ExerciseId, Exercise> getExercise) {
            Exercise exercise = getExercise.apply(exerciseId);
            if (STATUS_SET.contains(exercise.getExerciseStatus())) {
                throw new CustomException(ResponseCode.NOT_ALLOWED_DELETE_EXERCISE_USER);
            }
            return ExecuteStep.of(exercise);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final Exercise exercise;

        public PersistStep leave(UserId userId) {
            ExerciseUser exerciseUser;
            if (exercise instanceof ClubExercise clubExercise) {
                exerciseUser = clubExercise.expelMember(userId);
            } else {
                exerciseUser = exercise.expelMember(userId);
            }
            return PersistStep.of(exercise, exerciseUser);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final Exercise exercise;
        private final ExerciseUser exerciseUser;

        public PersistStep saveExercise(final Consumer<Exercise> saveExercise) {
            saveExercise.accept(exercise);
            return this;
        }

        public PersistStep deleteExerciseUser(final Consumer<ExerciseUser> deleteExerciseUser) {
            deleteExerciseUser.accept(exerciseUser);
            return this;
        }

        public EventStep deleteMatchUser(final BiConsumer<ExerciseId, UserId> deleteMatchUser) {
            deleteMatchUser.accept(exercise.getExerciseId(), exerciseUser.getUserId());
            return EventStep.of(exercise, exerciseUser);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class EventStep {

        private final Exercise exercise;
        private final ExerciseUser exerciseUser;

        public void produce(
            final BiConsumer<Exercise, ExerciseUser> producer
        ) {
            producer.accept(exercise, exerciseUser);
        }
    }
}
