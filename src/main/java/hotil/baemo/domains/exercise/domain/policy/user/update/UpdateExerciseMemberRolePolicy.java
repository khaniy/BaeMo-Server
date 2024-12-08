package hotil.baemo.domains.exercise.domain.policy.user.update;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateExerciseMemberRolePolicy {

    private final UserId userId;
    private final ExerciseId exerciseId;


    public static UpdateExerciseMemberRolePolicy execute(UserId userId, ExerciseId exerciseId) {
        return new UpdateExerciseMemberRolePolicy(userId, exerciseId);
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

        public PersistStep appointMemberToAdmin(UserId memberId) {
            ExerciseUser exerciseUser = exercise.appointMemberToAdmin(memberId);
            return PersistStep.of(exercise, exerciseUser);
        }

        public PersistStep downgradeAdminToMember(UserId adminId) {
            ExerciseUser exerciseUser = exercise.downgradeAdminToMember(adminId);
            return PersistStep.of(exercise, exerciseUser);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final Exercise exercise;
        private final ExerciseUser exerciseUser;

        public EventStep persist(final BiConsumer<ExerciseId, ExerciseUser> repository) {
            repository.accept(exercise.getExerciseId(), exerciseUser);
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
