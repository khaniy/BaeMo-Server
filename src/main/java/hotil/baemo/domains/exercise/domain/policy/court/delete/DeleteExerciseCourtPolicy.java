package hotil.baemo.domains.exercise.domain.policy.court.delete;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.entity.court.ExerciseCourt;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseCourtId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteExerciseCourtPolicy {

    private final UserId userId;
    private final ExerciseId exerciseId;
    private final ExerciseCourtId exerciseCourtId;

    public static DeleteExerciseCourtPolicy execute(UserId userId, ExerciseId exerciseId, ExerciseCourtId exerciseCourtId) {
        return new DeleteExerciseCourtPolicy(userId, exerciseId, exerciseCourtId);
    }

    public LoadStep valid(final BiFunction<ExerciseId, UserId, ExerciseUser> getRule) {
        ExerciseUser rule = getRule.apply(exerciseId, userId);
        if (!rule.isAdmin()) {
            throw new CustomException(ResponseCode.EXERCISE_ROLE_AUTH_FAILED);
        }
        return LoadStep.of(exerciseCourtId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class LoadStep {

        private final ExerciseCourtId exerciseCourtId;

        public CheckStep load(final Function<ExerciseCourtId, ExerciseCourt> getExerciseCourt) {
            ExerciseCourt exerciseCourt = getExerciseCourt.apply(exerciseCourtId);
            return CheckStep.of(exerciseCourt);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class CheckStep {

        private final ExerciseCourt exerciseCourt;

        public PersistStep check(final Function<ExerciseCourt, Boolean> getExerciseCourt) {
            boolean isProgressCourt = getExerciseCourt.apply(exerciseCourt);
            if (isProgressCourt) {
                throw new CustomException(ResponseCode.EXERCISE_COURT_NOT_DELETED);
            }
            return PersistStep.of(exerciseCourt);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final ExerciseCourt exerciseCourt;

        public void delete(final Consumer<ExerciseCourt> repository) {
            repository.accept(exerciseCourt);
        }
    }
}
