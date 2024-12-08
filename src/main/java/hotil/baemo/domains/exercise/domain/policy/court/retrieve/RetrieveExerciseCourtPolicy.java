package hotil.baemo.domains.exercise.domain.policy.court.retrieve;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.application.dto.QExerciseCourtDTO;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveExerciseCourtPolicy {

    private final UserId userId;
    private final ExerciseId exerciseId;


    public static RetrieveExerciseCourtPolicy execute(UserId userId, ExerciseId exerciseId) {
        return new RetrieveExerciseCourtPolicy(userId, exerciseId);
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

        public List<QExerciseCourtDTO.ExerciseCourt> load(final Function<ExerciseId, List<QExerciseCourtDTO.ExerciseCourt>> getExerciseCourts) {
            return getExerciseCourts.apply(exerciseId);
        }
    }
}
