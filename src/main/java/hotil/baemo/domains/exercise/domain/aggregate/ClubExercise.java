package hotil.baemo.domains.exercise.domain.aggregate;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
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

    private ClubExercise(ExerciseId exerciseId, ExerciseType exerciseType, ExerciseUsers exerciseUsers, Title title, Description description, hotil.baemo.domains.exercise.domain.value.exercise.ParticipantNumber participantLimit, hotil.baemo.domains.exercise.domain.value.exercise.ParticipantNumber currentParticipant, Location location, ExerciseTime exerciseTime, ExerciseStatus exerciseStatus, ExerciseThumbnail thumbnail, boolean isDel, ClubId clubId, ParticipantNumber guestLimit, hotil.baemo.domains.exercise.domain.value.exercise.ParticipantNumber currentParticipantGuest) {
        super(exerciseId, exerciseType, exerciseUsers, title, description, participantLimit, currentParticipant, location, exerciseTime, exerciseStatus, thumbnail, isDel);
        this.clubId = clubId;
        this.guestLimit = guestLimit;
        this.currentParticipantGuest = currentParticipantGuest;
    }

    public static ClubExercise init(
        ClubId clubId,
        Title title,
        Description description,
        ParticipantNumber participantLimit,
        ParticipantNumber guestLimit,
        Location location,
        ExerciseTime exerciseTime,
        ExerciseUser exerciseUser
    ) {
        return ClubExercise.builder()
            .clubId(clubId)
            .title(title)
            .description(description)
            .participantLimit(participantLimit)
            .currentParticipant(new ParticipantNumber(1))
            .guestLimit(guestLimit)
            .currentParticipantGuest(new ParticipantNumber(0))
            .location(location)
            .exerciseTime(exerciseTime)
            .exerciseStatus(ExerciseStatus.RECRUITING)
            .exerciseType(ExerciseType.CLUB)
            .exerciseUsers(ExerciseUsers.init(exerciseUser))
            .build();
    }

    public void update(Title title, Description description, ParticipantNumber participantLimit, ParticipantNumber guestLimit, Location location, ExerciseTime exerciseTime) {
        super.update(title, description, participantLimit, location, exerciseTime);
        this.guestLimit = guestLimit;
        if (currentParticipantGuest.number() > guestLimit.number()) {
            throw new CustomException(ResponseCode.UNACCEPTABLE_GUEST_LIMIT);
        }
    }

    public ExerciseUser approvePendingGuest(UserId targetUserId) {
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

    private boolean isOverGuestLimit() {
        return currentParticipantGuest.number() >= guestLimit.number();
    }

    @Override
    public void progressAuto() {
        if (exerciseStatus.equals(ExerciseStatus.PROGRESS) || exerciseStatus.equals(ExerciseStatus.COMPLETE)) {
            return;
        }
        if (currentParticipant.number() + currentParticipantGuest.number() < ParticipantNumber.MIN_PARTICIPANT) {
            return;
        }
        ZonedDateTime start = ZonedDateTime.now();
        ZonedDateTime end = this.exerciseTime.endTime().isBefore(start.plusHours(ExerciseTime.MIN_TIME))
            ? start.plusHours(ExerciseTime.MIN_TIME)
            : this.exerciseTime.endTime();

        this.exerciseStatus = ExerciseStatus.PROGRESS;
        this.exerciseTime = new ExerciseTime(start, end);
    }
}