package hotil.baemo.domains.exercise.domain.specification.exercise.command;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.exercise.domain.roles.ExerciseRule;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserMatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateExerciseUserSpecification {

    private final ExerciseRule rule;
    private static final EnumSet<ExerciseStatus> STATUS_ROLE = EnumSet.of(ExerciseStatus.RECRUITING, ExerciseStatus.RECRUITMENT_FINISHED);

    public static CreateExerciseUserSpecification spec(ExerciseRule rule, Exercise exercise) {
        Rules rules = switch (exercise.getExerciseType()) {
            case CLUB -> Rules.CLUB_RULE;
            case IMPROMPTU -> Rules.IMPROMPTU_RULE;
        };

        if (!rules.actorRule.test(rule)) {
            if(rules.equals(Rules.CLUB_RULE)) {
                throw new CustomException(ResponseCode.NOT_CLUB_MEMBER);
            }
            throw new CustomException(ResponseCode.EXERCISE_ROLE_AUTH_FAILED);
        }

        if (!rules.statusRule.test(exercise.getExerciseStatus())) {
            throw new CustomException(ResponseCode.NOT_ALLOWED_CREATE_EXERCISE_USER);
        }
        return new CreateExerciseUserSpecification(rule);
    }

    public ExerciseUser participate(UserId userId, Exercise exercise) {
        ExerciseUser exerciseUser = switch (exercise.getExerciseType()) {
            case CLUB -> switch (rule.getRole()) {
                case ADMIN -> ExerciseUser.newParticipatedUser(userId, ExerciseUserRole.ADMIN);
                case MEMBER -> ExerciseUser.newParticipatedUser(userId, ExerciseUserRole.MEMBER);
                default -> throw new CustomException(ResponseCode.IS_NOT_CLUB_USER);
            };
            case IMPROMPTU -> ExerciseUser.newPendingUser(userId, ExerciseUserRole.MEMBER);
        };
        return exercise.addUser(exerciseUser);
    }

    public ExerciseUser applyGuest(UserId userId, Exercise exercise, UserId targetUserId, List<UserId> clubUserIds) {
        if(clubUserIds.contains(targetUserId)) {
            throw new CustomException(ResponseCode.CLUB_USER_CANT_BE_GUEST);
        }
        ExerciseUser exerciseUser = ExerciseUser.newGuest(targetUserId, userId);
        return exercise.addUser(exerciseUser);
    }


    @RequiredArgsConstructor
    private enum Rules {
        CLUB_RULE(
            ExerciseRule::isExerciseMemberRule,
            STATUS_ROLE::contains
        ),
        IMPROMPTU_RULE(
            ExerciseRule::isNotParticipantRule,
            STATUS_ROLE::contains
        );

        private final Predicate<ExerciseRule> actorRule;
        private final Predicate<ExerciseStatus> statusRule;
    }
}