package hotil.baemo.domains.exercise.domain.policy.exercise.create;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.entity.exercise.ClubExercise;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.ClubExerciseVOGroup;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseThumbnail;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateClubExercisePolicy {

    private final UserId userId;
    private final ClubId clubId;

    public static CreateClubExercisePolicy execute(UserId userId, ClubId clubId) {
        return new CreateClubExercisePolicy(userId, clubId);
    }

    public ExecuteStep valid(final BiFunction<ClubId, UserId, ExerciseUser> check) {
        ExerciseUser rule = check.apply(clubId, userId);
        if (!rule.isAdmin()) {
            throw new CustomException(ResponseCode.EXERCISE_ROLE_AUTH_FAILED);
        }
        return ExecuteStep.of(userId, clubId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final UserId userId;
        private final ClubId clubId;

        public PersistStep create(ClubExerciseVOGroup aggregate) {
            ClubExercise exercise = ClubExercise.of(userId, clubId, aggregate);
            return new PersistStep(userId, exercise, aggregate.thumbnail());
        }
    }


    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class PersistStep {

        private final UserId userId;
        private final ClubExercise exercise;
        private final ExerciseThumbnail exerciseThumbnail;

        public EventStep persist(final BiFunction<ClubExercise, ExerciseThumbnail, Exercise> repository) {
            Exercise createdExercise = repository.apply(this.exercise, exerciseThumbnail);
            return EventStep.of(userId, createdExercise);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class EventStep {

        private final UserId userId;
        private final Exercise exercise;

        public void produce(final BiConsumer<Exercise, UserId> event) {
            event.accept(exercise, userId);
        }
    }

}
