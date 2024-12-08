package hotil.baemo.domains.exercise.domain.specification;

import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.club.ClubRole;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ExerciseUserSpec {

    private final UserId userId;

    public static ExerciseUserSpec of(UserId userId) {
        return new ExerciseUserSpec(userId);
    }

    public ExerciseUser participantAdmin() {
        return ExerciseUser.builder()
            .userId(userId)
            .role(ExerciseUserRole.ADMIN)
            .status(ExerciseUserStatus.PARTICIPATE)
            .matchStatus(MatchStatus.NO_MATCH)
            .appliedBy(userId)
            .build();
    }

    public ExerciseUser participantMember() {
        return ExerciseUser.builder()
            .userId(userId)
            .role(ExerciseUserRole.MEMBER)
            .status(ExerciseUserStatus.PARTICIPATE)
            .matchStatus(MatchStatus.NO_MATCH)
            .appliedBy(userId)
            .build();
    }

    public ExerciseUser pendingMember() {
        return ExerciseUser.builder()
            .userId(userId)
            .role(ExerciseUserRole.MEMBER)
            .status(ExerciseUserStatus.PENDING)
            .matchStatus(MatchStatus.NO_MATCH)
            .appliedBy(userId)
            .build();
    }

    public ExerciseUser pendingGuest(UserId appliedBy) {
        return ExerciseUser.builder()
            .userId(userId)
            .role(ExerciseUserRole.GUEST)
            .status(ExerciseUserStatus.PENDING)
            .matchStatus(MatchStatus.NO_MATCH)
            .appliedBy(appliedBy)
            .build();
    }

    public ExerciseUser nonMember() {
        return ExerciseUser.builder()
            .userId(userId)
            .role(ExerciseUserRole.NON_MEMBER)
            .status(ExerciseUserStatus.NOT_PARTICIPATE)
            .matchStatus(MatchStatus.NO_MATCH)
            .appliedBy(userId)
            .build();
    }

    public ExerciseUser fromClubRole(ClubRole clubRole) {
        return ExerciseUser.builder()
            .userId(userId)
            .role(clubRole.toExerciseUserRole())
            .status(ExerciseUserStatus.NOT_PARTICIPATE)
            .matchStatus(MatchStatus.NO_MATCH)
            .appliedBy(userId)
            .build();
    }
}
