package hotil.baemo.domains.exercise.domain.policy.user.update;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.entity.exercise.ClubExercise;
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
public class ApprovePendingGuestPolicy {

    private final UserId userId;
    private final ExerciseId exerciseId;


    public static ApprovePendingGuestPolicy execute(UserId userId, ExerciseId exerciseId) {
        return new ApprovePendingGuestPolicy(userId, exerciseId);
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

        public ExecuteStep get(final Function<ExerciseId, ClubExercise> getExercise) {
            ClubExercise clubExercise = getExercise.apply(exerciseId);
            return ExecuteStep.of(clubExercise);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final ClubExercise clubExercise;

        public PersistStep approvePendingGuest(UserId pendingUserId) {
            ExerciseUser approvedUser = clubExercise.approvePendingGuest(pendingUserId);
            return PersistStep.of(clubExercise, approvedUser);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final ClubExercise clubExercise;
        private final ExerciseUser approvedUser;

        public EventStep persist(final Consumer<ClubExercise> repository) {
            repository.accept(clubExercise);
            return EventStep.of(clubExercise, approvedUser);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class EventStep {

        private final ClubExercise clubExercise;
        private final ExerciseUser approvedUser;

        public void produce(
            final BiConsumer<Exercise, ExerciseUser> producer1,
            final BiConsumer<Exercise, ExerciseUser> producer2
        ) {
            producer1.accept(clubExercise, approvedUser);
            producer2.accept(clubExercise, approvedUser);
        }
    }
}
