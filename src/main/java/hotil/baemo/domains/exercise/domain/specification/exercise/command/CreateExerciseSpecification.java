package hotil.baemo.domains.exercise.domain.specification.exercise.command;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.aggregate.ClubExercise;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.exercise.domain.roles.ExerciseRule;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.*;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserMatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateExerciseSpecification {

    public static CreateExerciseSpecification spec(ExerciseRule rule) {
        if (!Rules.CREATE_RULE.actorPredicate.test(rule)) {
            throw new CustomException(ResponseCode.EXERCISE_ROLE_AUTH_FAILED);
        }
        return new CreateExerciseSpecification();
    }

    public Exercise createExercise(UserId userId, Title title, Description description, ParticipantNumber participantLimit, Location location, ExerciseTime exerciseTime) {
        ExerciseUser exerciseUser = ExerciseUser.builder()
            .userId(userId)
            .role(ExerciseUserRole.ADMIN)
            .status(ExerciseUserStatus.PARTICIPATE)
            .matchStatus(ExerciseUserMatchStatus.WAITING)
            .appliedBy(userId)
            .build();
        return Exercise.init(title, description, participantLimit, location, exerciseTime, exerciseUser);
    }

    public ClubExercise createClubExercise(UserId userId, ClubId clubId, ParticipantNumber guestLimit, Title title, Description description, ParticipantNumber participantLimit, Location location, ExerciseTime exerciseTime) {
        ExerciseUser exerciseUser = ExerciseUser.builder()
            .userId(userId)
            .role(ExerciseUserRole.ADMIN)
            .status(ExerciseUserStatus.PARTICIPATE)
            .matchStatus(ExerciseUserMatchStatus.WAITING)
            .appliedBy(userId)
            .build();
        return ClubExercise.init(clubId, title, description, participantLimit, guestLimit, location, exerciseTime, exerciseUser);
    }

    @RequiredArgsConstructor
    private enum Rules {
        CREATE_RULE(ExerciseRule::isAdminRule);
        private final Predicate<ExerciseRule> actorPredicate;
    }
}
