package hotil.baemo.domains.exercise.domain.policy.user.delete;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RejectPendingGuestPolicy {

    private final UserId userId;
    private final ExerciseId exerciseId;


    public static RejectPendingGuestPolicy execute(UserId userId, ExerciseId exerciseId) {
        return new RejectPendingGuestPolicy(userId, exerciseId);
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

        public ExecuteStep get(final Function<ExerciseId, Exercise> getExercise) {
            Exercise exercise = getExercise.apply(exerciseId);
            return ExecuteStep.of(exercise);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final Exercise exercise;

        public PersistStep rejectPendingUser(UserId memberId) {
            ExerciseUser exerciseUser = exercise.rejectPendingMember(memberId);
            return PersistStep.of(exerciseUser);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final ExerciseUser exerciseUser;

        public void persist(final Consumer<ExerciseUser> repository1) {
            repository1.accept(exerciseUser);
        }
    }
}
