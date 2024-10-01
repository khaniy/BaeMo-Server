package hotil.baemo.domains.exercise.domain.specification.exercise.command;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.aggregate.ClubExercise;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.exercise.domain.roles.ExerciseRule;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateExerciseUserSpecification {

    public static UpdateExerciseUserSpecification spec(ExerciseRule role, Exercise exercise) {
        if (!Rules.UPDATE_RULE.actorRule.test(role)) {
            throw new CustomException(ResponseCode.EXERCISE_ROLE_AUTH_FAILED);
        }
        if (!Rules.UPDATE_RULE.statusRule.test(exercise.getExerciseStatus())) {
            throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_EXERCISE_USER);
        }
        return new UpdateExerciseUserSpecification();
    }

    public ExerciseUser approvePendingMember(Exercise exercise, UserId targetUserId) {
        return exercise.approvePendingMember(targetUserId);

    }

    public ExerciseUser approvePendingGuest(ClubExercise clubExercise, UserId targetUserId) {
        return clubExercise.approvePendingGuest(targetUserId);
    }

    public ExerciseUser rejectPendingMember(Exercise exercise, UserId targetUserId) {
        return exercise.rejectPendingMember(targetUserId);

    }

    public ExerciseUser rejectPendingGuest(ClubExercise clubExercise, UserId targetUserId) {
        return clubExercise.rejectPendingGuest(targetUserId);
    }

    public ExerciseUser expelExerciseUser(Exercise exercise, UserId targetUserId) {
        return exercise.expelExerciseUser(targetUserId);
    }

    public ExerciseUser appointMemberToAdmin(Exercise exercise, UserId targetUserId) {
        return exercise.appointMemberToAdmin(targetUserId);
    }

    public ExerciseUser downgradeAdminToMember(Exercise exercise, UserId targetUserId) {
        return exercise.downgradeAdminToMember(targetUserId);
    }


    @RequiredArgsConstructor
    private enum Rules {
        UPDATE_RULE(
            ExerciseRule::isAdminRule,
            status -> EnumSet.of(ExerciseStatus.RECRUITING, ExerciseStatus.RECRUITMENT_FINISHED).contains(status)),

        ;
        private final Predicate<ExerciseRule> actorRule;
        private final Predicate<ExerciseStatus> statusRule;
    }
}
