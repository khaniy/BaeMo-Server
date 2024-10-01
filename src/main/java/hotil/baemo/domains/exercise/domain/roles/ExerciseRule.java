package hotil.baemo.domains.exercise.domain.roles;

import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.club.ClubRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC, staticName = "of")
public class ExerciseRule {
    private ExerciseUserStatus status;
    private ExerciseUserRole role;

    public static ExerciseRule toNotParticipatedRule(){
        return ExerciseRule.of(ExerciseUserStatus.NOT_PARTICIPATE, ExerciseUserRole.NON_MEMBER);
    }

    public static ExerciseRule fromExerciseUser(ExerciseUser user) {
        return ExerciseRule.of(user.getStatus(), user.getRole());
    }

    public static ExerciseRule fromClubRole(ClubRole role) {
        return switch (role) {
            case ADMIN, MANAGER -> ExerciseRule.of(ExerciseUserStatus.NOT_PARTICIPATE, ExerciseUserRole.ADMIN);
            case MEMBER -> ExerciseRule.of(ExerciseUserStatus.NOT_PARTICIPATE, ExerciseUserRole.MEMBER);
            default -> toNotParticipatedRule();
        };
    }

    public boolean isAdminRule(){
        return ExerciseUserRole.ADMIN == role;
    }

    public boolean isExerciseMemberRule(){
        return EnumSet.of(ExerciseUserRole.MEMBER, ExerciseUserRole.ADMIN).contains(role);
    }

    public boolean isNotParticipantRule() {
        return status == ExerciseUserStatus.NOT_PARTICIPATE && role == ExerciseUserRole.NON_MEMBER;
    }
}