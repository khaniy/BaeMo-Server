package hotil.baemo.domains.exercise.domain.aggregate;

import hotil.baemo.domains.exercise.domain.value.user.*;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.EnumSet;


@Getter
public class ExerciseUser {
    private final ExerciseUserId id;
    private final UserId userId;
    private ExerciseUserStatus status;
    private ExerciseUserRole role;
    private ExerciseUserMatchStatus matchStatus;
    private UserId appliedBy;
    private final ZonedDateTime updatedAt;

    public static ExerciseUser newParticipatedUser(UserId userId, ExerciseUserRole role) {
        return ExerciseUser.builder()
            .userId(userId)
            .role(role)
            .status(ExerciseUserStatus.PARTICIPATE)
            .matchStatus(ExerciseUserMatchStatus.WAITING)
            .appliedBy(userId)
            .build();
    }

    public static ExerciseUser newPendingUser(UserId userId, ExerciseUserRole role) {
        return ExerciseUser.builder()
            .userId(userId)
            .role(role)
            .status(ExerciseUserStatus.PENDING)
            .matchStatus(ExerciseUserMatchStatus.WAITING)
            .appliedBy(userId)
            .build();
    }

    public static ExerciseUser newGuest(UserId userId, UserId appliedBy) {
        return ExerciseUser.builder()
            .userId(userId)
            .role(ExerciseUserRole.GUEST)
            .status(ExerciseUserStatus.PENDING)
            .matchStatus(ExerciseUserMatchStatus.WAITING)
            .appliedBy(appliedBy)
            .build();
    }

    public boolean isParticipateUser() {
        return status == ExerciseUserStatus.PARTICIPATE;
    }

    public boolean isParticipateMember() {
        return status == ExerciseUserStatus.PARTICIPATE
            && EnumSet.of(ExerciseUserRole.MEMBER, ExerciseUserRole.ADMIN).contains(role);
    }

    public boolean isParticipateGuest() {
        return status == ExerciseUserStatus.PARTICIPATE
            && role == ExerciseUserRole.GUEST;
    }

    public boolean isParticipateAdmin() {
        return status == ExerciseUserStatus.PARTICIPATE
            && role == ExerciseUserRole.ADMIN;
    }

    public boolean isWaitingMember() {
        return status == ExerciseUserStatus.WAITING
            && EnumSet.of(ExerciseUserRole.MEMBER, ExerciseUserRole.ADMIN).contains(role);
    }

    public boolean isPendingUser() {
        return status == ExerciseUserStatus.PENDING;
    }

    public boolean isAdmin() {
        return role == ExerciseUserRole.ADMIN;
    }

    public boolean isPendingGuest() {
        return status == ExerciseUserStatus.PENDING
            && role == ExerciseUserRole.GUEST;
    }

    public void toWaitingUser() {
        status = ExerciseUserStatus.WAITING;
    }

    public void toParticipateUser() {
        status = ExerciseUserStatus.PARTICIPATE;
    }

    public void toAdmin() {
        role = ExerciseUserRole.ADMIN;
    }

    public void toMember() {
        role = ExerciseUserRole.MEMBER;
    }

    @Builder
    private ExerciseUser(ExerciseUserId id, UserId userId, ExerciseUserStatus status, ExerciseUserRole role, ExerciseUserMatchStatus matchStatus, UserId appliedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.role = role;
        this.matchStatus = matchStatus;
        this.appliedBy = appliedBy;
        this.updatedAt = updatedAt;
    }
}
