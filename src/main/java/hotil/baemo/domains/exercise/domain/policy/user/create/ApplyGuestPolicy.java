package hotil.baemo.domains.exercise.domain.policy.user.create;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.entity.exercise.ClubExercise;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.specification.ExerciseUserSpec;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplyGuestPolicy {

    private final UserId userId;
    private final ExerciseId exerciseId;


    public static ApplyGuestPolicy execute(UserId userId, ExerciseId exerciseId) {
        return new ApplyGuestPolicy(userId, exerciseId);
    }

    public ValidStep get(final Function<ExerciseId, ClubExercise> getExercise) {
        ClubExercise exercise = getExercise.apply(exerciseId);
        return ValidStep.of(exercise, userId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ValidStep {

        private final ClubExercise clubExercise;
        private final UserId userId;

        public CheckStep valid(final BiFunction<ClubId, UserId, ExerciseUser> getRule) {
            ExerciseUser rule = getRule.apply(clubExercise.getClubId(), userId);
            if (rule.isNonMember()) {
                throw new CustomException(ResponseCode.NOT_CLUB_MEMBER);
            }
            return CheckStep.of(clubExercise, userId);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class CheckStep {

        private final ClubExercise clubExercise;
        private final UserId userId;

        public ExecuteStep check(final BiFunction<UserId, ClubId, Boolean> checkUser) {
            if (!checkUser.apply(userId, clubExercise.getClubId())) {
                throw new CustomException(ResponseCode.CLUB_USER_CANT_BE_GUEST);
            }
            return ExecuteStep.of(clubExercise);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final ClubExercise clubExercise;

        public PersistStep applyGuest(UserId userId, UserId guestUserId) {
            ExerciseUser exerciseUser = clubExercise.applyGuest(userId, guestUserId);
            return PersistStep.of(clubExercise, exerciseUser);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final ClubExercise exercise;
        private final ExerciseUser exerciseUser;

        public EventStep persist(final Consumer<Exercise> repository) {
            repository.accept(exercise);
            return EventStep.of(exercise, exerciseUser);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class EventStep {

        private final ClubExercise exercise;
        private final ExerciseUser exerciseUser;

        public void produce(final BiConsumer<ClubExercise, ExerciseUser> producer) {
            producer.accept(exercise, exerciseUser);
        }
    }

}
