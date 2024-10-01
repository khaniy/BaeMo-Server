package hotil.baemo.domains.exercise.domain.specification.exercise.command;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.aggregate.ClubExercise;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.roles.ExerciseRule;
import hotil.baemo.domains.exercise.domain.value.exercise.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateExerciseSpecification {

    public static UpdateExerciseSpecification spec(ExerciseRule role, Exercise exercise) {
        if (!Rules.UPDATE_RULE.actorPredicate.test(role)) {
            throw new CustomException(ResponseCode.EXERCISE_ROLE_AUTH_FAILED);
        }
        if (!Rules.UPDATE_RULE.statusPredicate.test(exercise.getExerciseStatus())) {
            throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_EXERCISE);
        }
        return new UpdateExerciseSpecification();
    }

    public void updateExercise(Exercise exercise, Title title, Description description, ParticipantNumber participantLimit, Location location, ExerciseTime exerciseTime) {
        exercise.update(title, description, participantLimit, location, exerciseTime);
    }

    public void updateClubExercise(ClubExercise clubExercise, Title title, ParticipantNumber guestLimit, Description description, ParticipantNumber participantLimit, Location location, ExerciseTime exerciseTime) {
        clubExercise.update(title, description, participantLimit, guestLimit, location, exerciseTime);
    }

    @RequiredArgsConstructor
    private enum Rules {
        UPDATE_RULE(
            ExerciseRule::isAdminRule,
            status -> EnumSet.of(ExerciseStatus.RECRUITING, ExerciseStatus.RECRUITMENT_FINISHED).contains(status)),

        ;
        private final Predicate<ExerciseRule> actorPredicate;
        private final Predicate<ExerciseStatus> statusPredicate;
    }
}
