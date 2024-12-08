package hotil.baemo.domains.exercise.domain.entity.exercise;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUsers;
import hotil.baemo.domains.exercise.domain.specification.ExerciseUserSpec;
import hotil.baemo.domains.exercise.domain.value.ClubExerciseVOGroup;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.*;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Getter
@SuperBuilder
public class ClubExercise extends Exercise {
    private ClubId clubId;
    private ParticipantNumber guestLimit;
    private ParticipantNumber currentParticipantGuest;

    private ClubExercise(ExerciseId exerciseId, ExerciseType exerciseType, ExerciseUsers exerciseUsers, Title title, Description description, hotil.baemo.domains.exercise.domain.value.exercise.ParticipantNumber participantLimit, hotil.baemo.domains.exercise.domain.value.exercise.ParticipantNumber currentParticipant, Location location, Address address, LocationCode locationCode, Coordinate coordinate, ExerciseTime exerciseTime, ExerciseStatus exerciseStatus, ExerciseThumbnailUrl thumbnailUrl, boolean isDel, ClubId clubId, ParticipantNumber guestLimit, ParticipantNumber currentParticipantGuest) {
        super(exerciseId, exerciseType, exerciseStatus, exerciseTime, exerciseUsers, title, description, participantLimit, currentParticipant, location, address, locationCode, coordinate, thumbnailUrl, isDel);
        this.clubId = clubId;
        this.guestLimit = guestLimit;
        this.currentParticipantGuest = currentParticipantGuest;
    }

    public static ClubExercise of(UserId userId, ClubId clubId, ClubExerciseVOGroup voGroup) {
        ExerciseUser exerciseUser = ExerciseUserSpec.of(userId).participantAdmin();
        return ClubExercise.builder()
            .exerciseType(ExerciseType.CLUB)
            .clubId(clubId)
            .title(voGroup.title())
            .description(voGroup.description())
            .guestLimit(voGroup.guestLimit())
            .currentParticipantGuest(new ParticipantNumber(0))
            .participantLimit(voGroup.participantLimit())
            .currentParticipant(new ParticipantNumber(1))
            .location(voGroup.location())
            .address(voGroup.address())
            .locationCode(voGroup.locationCode())
            .coordinate(voGroup.coordinate())
            .exerciseTime(voGroup.exerciseTime())
            .exerciseStatus(ExerciseStatus.RECRUITING)
            .exerciseUsers(ExerciseUsers.init(exerciseUser))
            .isDel(false)
            .build();
    }

    public void update(ClubExerciseVOGroup aggregate) {
        if (exerciseStatus.equals(ExerciseStatus.COMPLETE)) {
            throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_EXERCISE);
        }
        if (currentParticipantGuest.number() > guestLimit.number()) {
            throw new CustomException(ResponseCode.UNACCEPTABLE_GUEST_LIMIT);
        }
        if (currentParticipant.number() > participantLimit.number()) {
            throw new CustomException(ResponseCode.UNACCEPTABLE_PARTICIPANT_LIMIT);
        }
        this.title = aggregate.title();
        this.description = aggregate.description();
        this.participantLimit = aggregate.participantLimit();
        this.location = aggregate.location();
        this.address = aggregate.address();
        this.locationCode = aggregate.locationCode();
        this.coordinate = aggregate.coordinate();
        this.exerciseTime = aggregate.exerciseTime();
        this.guestLimit = aggregate.guestLimit();
    }

    public ExerciseUser participate(UserId userId, ExerciseUser user) {
        ExerciseUser exerciseUser = switch (user.getRole()) {
            case ADMIN -> ExerciseUserSpec.of(userId).participantAdmin();
            case MEMBER -> ExerciseUserSpec.of(userId).participantMember();
            default -> ExerciseUserSpec.of(userId).pendingGuest(userId);
        };
        return addUser(exerciseUser);
    }

    public ExerciseUser applyGuest(UserId userId, UserId guestUserId) {
        if (exerciseStatus.equals(ExerciseStatus.COMPLETE)) {
            throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_EXERCISE);
        }
        ExerciseUser exerciseUser = ExerciseUserSpec.of(guestUserId).pendingGuest(userId);
        return addUser(exerciseUser);
    }

    public ExerciseUser approvePendingGuest(UserId targetUserId) {
        if (exerciseStatus.equals(ExerciseStatus.COMPLETE)) {
            throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_EXERCISE);
        }
        ExerciseUser targetUser = exerciseUsers.pop(targetUserId);
        if (!targetUser.isPendingGuest()) {
            throw new CustomException(ResponseCode.IS_NOT_PENDING_GUEST_USER);
        }
        targetUser.toParticipateUser();
        return addGuest(targetUser);
    }

    public ExerciseUser rejectPendingGuest(UserId targetUserId) {
        ExerciseUser targetUser = exerciseUsers.pop(targetUserId);
        if (!targetUser.isPendingGuest()) {
            throw new CustomException(ResponseCode.IS_NOT_PENDING_GUEST_USER);
        }
        this.currentParticipantGuest = new ParticipantNumber(exerciseUsers.countCurrentParticipateGuest());
        return targetUser;
    }

    private ExerciseUser addGuest(ExerciseUser exerciseUser) {
        if (exerciseStatus.equals(ExerciseStatus.COMPLETE)) {
            throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_EXERCISE);
        }
        if (exerciseUser.isParticipateGuest() && isOverGuestLimit()) {
            throw new CustomException(ResponseCode.EXCEED_GUEST_LIMIT);
        }
        exerciseUsers.add(exerciseUser);
        this.currentParticipantGuest = new ParticipantNumber(exerciseUsers.countCurrentParticipateGuest());
        return exerciseUser;
    }

    @Override
    public void progress() {
        if (exerciseStatus.equals(ExerciseStatus.PROGRESS)) {
            return;
        }
        if (currentParticipant.number() + currentParticipantGuest.number() < ParticipantNumber.MIN_PARTICIPANT) {
            throw new CustomException(ResponseCode.IS_PARTICIPANT_UNDER_4);
        }
        ZonedDateTime start = ZonedDateTime.now();
        ZonedDateTime end = this.exerciseTime.endTime().isBefore(start.plusHours(ExerciseTime.MIN_TIME))
            ? start.plusHours(ExerciseTime.MIN_TIME)
            : this.exerciseTime.endTime();

        this.exerciseStatus = ExerciseStatus.PROGRESS;
        this.exerciseTime = new ExerciseTime(start, end);
    }

    @Override
    public ExerciseUser expelMember(UserId targetUserId) {
        ExerciseUser exerciseUser = super.expelMember(targetUserId);
        setCurrentParticipantGuest();
        return exerciseUser;
    }

    private boolean isOverGuestLimit() {
        return currentParticipantGuest.number() >= guestLimit.number();
    }

    private void setCurrentParticipantGuest() {
        this.currentParticipantGuest = new ParticipantNumber(exerciseUsers.countCurrentParticipateGuest());
    }
}