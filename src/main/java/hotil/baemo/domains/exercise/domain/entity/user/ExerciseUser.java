package hotil.baemo.domains.exercise.domain.entity.user;

import hotil.baemo.core.validator.BaemoValidator;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.exercise.domain.value.user.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.EnumSet;


@Getter
public class ExerciseUser extends BaemoValidator {
    private final ExerciseUserId id;
    private final ExerciseId exerciseId;

    @NotNull
    private final UserId userId;
    @NotNull
    private final MatchStatus matchStatus;
    @NotNull
    private final UserId appliedBy;
    private final ZonedDateTime updatedAt;

    @NotNull
    private ExerciseUserStatus status;
    @NotNull
    private ExerciseUserRole role;

    @Builder
    private ExerciseUser(ExerciseUserId id, ExerciseId exerciseId, UserId userId, ExerciseUserStatus status, ExerciseUserRole role, MatchStatus matchStatus, UserId appliedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.userId = userId;
        this.status = status;
        this.role = role;
        this.matchStatus = matchStatus;
        this.appliedBy = appliedBy;
        this.updatedAt = updatedAt;
        valid();
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
    public boolean isNonMember() {
        return role == ExerciseUserRole.NON_MEMBER;
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
}
