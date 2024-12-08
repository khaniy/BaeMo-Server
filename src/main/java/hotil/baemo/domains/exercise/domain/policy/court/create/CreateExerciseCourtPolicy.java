package hotil.baemo.domains.exercise.domain.policy.court.create;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.entity.court.ExerciseCourt;
import hotil.baemo.domains.exercise.domain.entity.court.ExerciseCourts;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.CourtNumber;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateExerciseCourtPolicy {

    private final UserId userId;
    private final ExerciseId exerciseId;


    public static CreateExerciseCourtPolicy execute(UserId userId, ExerciseId exerciseId) {
        return new CreateExerciseCourtPolicy(userId, exerciseId);
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

        public ExecuteStep load(final Function<ExerciseId, ExerciseCourts> getExerciseCourts) {
            ExerciseCourts exerciseCourts = getExerciseCourts.apply(exerciseId);
            return ExecuteStep.of(exerciseId, exerciseCourts);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final ExerciseId exerciseId;
        private final ExerciseCourts exerciseCourts;

        public PersistStep create(CourtNumber courtNumber) {
            exerciseCourts.add(ExerciseCourt.builder()
                .exerciseId(exerciseId)
                .number(courtNumber)
                .build()
            );
            return PersistStep.of(exerciseCourts);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final ExerciseCourts exerciseCourts;

        public void persist(final Consumer<ExerciseCourts> repository) {
            repository.accept(exerciseCourts);
        }
    }
}
