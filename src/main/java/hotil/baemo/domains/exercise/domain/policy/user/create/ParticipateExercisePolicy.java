package hotil.baemo.domains.exercise.domain.policy.user.create;

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
public class ParticipateExercisePolicy {

    private final UserId userId;
    private final ExerciseId exerciseId;


    public static ParticipateExercisePolicy execute(UserId userId, ExerciseId exerciseId) {
        return new ParticipateExercisePolicy(userId, exerciseId);
    }

    public ValidStep get(final Function<ExerciseId, Exercise> getExercise) {
        Exercise exercise = getExercise.apply(exerciseId);
        return ValidStep.of(userId, exercise);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ValidStep {

        private final UserId userId;
        private final Exercise exercise;

        public ExecuteStep valid(final BiFunction<ClubId, UserId, ExerciseUser> getRuleFromClub) {
            ExerciseUser rule;
            if (exercise instanceof ClubExercise clubExercise) {
                rule = getRuleFromClub.apply(clubExercise.getClubId(), userId);
            } else {
                rule = ExerciseUserSpec.of(userId).participantMember();
            }
            return ExecuteStep.of(exercise, rule);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final Exercise exercise;
        private final ExerciseUser user;

        public PersistStep participate(UserId userId) {
            ExerciseUser exerciseUser;
            if (exercise instanceof ClubExercise clubExercise) {
                exerciseUser = clubExercise.participate(userId, user);
            }else {
                exerciseUser = exercise.applyParticipate(userId);
            }
            return PersistStep.of(exercise, exerciseUser);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final Exercise exercise;
        private final ExerciseUser exerciseUser;

        public EventStep persist(final Consumer<Exercise> repository) {
            repository.accept(exercise);
            return EventStep.of(exercise, exerciseUser);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class EventStep {

        private final Exercise exercise;
        private final ExerciseUser exerciseUser;

        public void produce(
            final BiConsumer<Exercise, ExerciseUser> userParticipated,
            final BiConsumer<Exercise, ExerciseUser> userApplied
        ) {
            switch (exercise.getExerciseType()) {
                case CLUB -> {
                    switch (exerciseUser.getRole()){
                        case ADMIN, MEMBER ->  userParticipated.accept(exercise, exerciseUser);
                        case GUEST ->  userApplied.accept(exercise, exerciseUser);
                        default -> {}
                    }
                }
                case IMPROMPTU -> userApplied.accept(exercise, exerciseUser);
            }
        }
    }
}
