package hotil.baemo.domains.exercise.domain.aggregate;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.value.exercise.*;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Getter
@SuperBuilder
public class Exercise {
    protected final ExerciseId exerciseId;
    protected ExerciseType exerciseType;

    protected ExerciseStatus exerciseStatus;
    protected ExerciseTime exerciseTime;
    protected ExerciseUsers exerciseUsers;

    protected Title title;
    protected Description description;
    protected ParticipantNumber participantLimit;
    protected ParticipantNumber currentParticipant;
    protected Location location;
    protected ExerciseThumbnail thumbnail;
    protected boolean isDel;


    protected Exercise(ExerciseId exerciseId, ExerciseType exerciseType, ExerciseUsers exerciseUsers, Title title, Description description, ParticipantNumber participantLimit, ParticipantNumber currentParticipant, Location location, ExerciseTime exerciseTime, ExerciseStatus exerciseStatus, ExerciseThumbnail thumbnail, boolean isDel) {
        this.exerciseId = exerciseId;
        this.exerciseType = exerciseType;
        this.exerciseUsers = exerciseUsers;
        this.title = title;
        this.description = description;
        this.participantLimit = participantLimit;
        this.currentParticipant = currentParticipant;
        this.location = location;
        this.exerciseTime = exerciseTime;
        this.exerciseStatus = exerciseStatus;
        this.thumbnail = thumbnail;
        this.isDel = isDel;
    }

    public static Exercise init(
        Title title,
        Description description,
        ParticipantNumber participantNumber,
        Location location,
        ExerciseTime exerciseTime,
        ExerciseUser exerciseUser
    ) {
        return Exercise.builder()
            .title(title)
            .description(description)
            .participantLimit(participantNumber)
            .currentParticipant(new ParticipantNumber(1))
            .location(location)
            .exerciseTime(exerciseTime)
            .exerciseStatus(ExerciseStatus.RECRUITING)
            .exerciseType(ExerciseType.IMPROMPTU)
            .exerciseUsers(ExerciseUsers.init(exerciseUser))
            .isDel(false)
            .build();
    }

    public void update(Title title, Description description, ParticipantNumber participantLimit, Location location, ExerciseTime exerciseTime) {
        this.title = title;
        this.description = description;
        this.participantLimit = participantLimit;
        this.location = location;
        this.exerciseTime = exerciseTime;
        if (isOverParticipantLimit()) {
            throw new CustomException(ResponseCode.UNACCEPTABLE_PARTICIPANT_LIMIT);
        }
    }

    public void updateThumbnail(ExerciseThumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }


    public ExerciseUser approvePendingMember(UserId targetUserId) {
        ExerciseUser targetUser = exerciseUsers.pop(targetUserId);
        if (!targetUser.isPendingUser()) {
            throw new CustomException(ResponseCode.IS_NOT_PENDING_USER);
        }
        targetUser.toParticipateUser();
        return addUser(targetUser);
    }

    public ExerciseUser rejectPendingMember(UserId targetUserId) {
        ExerciseUser targetUser = exerciseUsers.pop(targetUserId);
        if (!targetUser.isPendingUser()) {
            throw new CustomException(ResponseCode.IS_NOT_PENDING_USER);
        }
        return targetUser;
    }

    public ExerciseUser expelExerciseUser(UserId targetUserId) {
        ExerciseUser targetUser = exerciseUsers.pop(targetUserId);
        int availableNumber = countAvailableParticipantNumber();
        if (availableNumber > 0) {
            exerciseUsers.updateWaitingMemberToParticipate(availableNumber);
        }
        if (countAvailableParticipantNumber() > 0) {
            this.exerciseStatus = ExerciseStatus.RECRUITING;
        }
        updateCurrentParticipant();
        if (!hasNoParticipants() && exerciseUsers.countCurrentParticipateAdmin() == 0) {
            throw new CustomException(ResponseCode.EXERCISE_NO_ADMIN);
        }
        return targetUser;
    }

    public ExerciseUser appointMemberToAdmin(UserId targetUserId) {
        ExerciseUser targetUser = exerciseUsers.pop(targetUserId);
        if (!targetUser.isParticipateMember()) {
            throw new CustomException(ResponseCode.IS_NOT_PARTICIPATE_MEMBER);
        }
        targetUser.toAdmin();
        return targetUser;
    }

    public ExerciseUser downgradeAdminToMember(UserId targetUserId) {
        ExerciseUser targetUser = exerciseUsers.pop(targetUserId);
        if (!targetUser.isAdmin()) { //Todo 모임 관리자인경우는 어떻게??
            throw new CustomException(ResponseCode.IS_NOT_EXERCISE_ADMIN);
        }
        targetUser.toMember();
        return targetUser;
    }


    public ExerciseUser addUser(ExerciseUser exerciseUser) {
        if (exerciseUser.isParticipateUser() && isOverParticipantLimit()) {
            exerciseUser.toWaitingUser();
            this.exerciseStatus = ExerciseStatus.RECRUITMENT_FINISHED;
        }
        exerciseUsers.add(exerciseUser);
        updateCurrentParticipant();
        return exerciseUser;
    }

    public Boolean hasNoParticipants() {
        return exerciseUsers.countCurrentParticipateMember() == 0;
    }

    public void delete() {
        this.isDel = true;
    }

    public void progress() {
        if (exerciseStatus.equals(ExerciseStatus.PROGRESS)) {
            return;
        }
        if (currentParticipant.number() < ParticipantNumber.MIN_PARTICIPANT) {
            throw new CustomException(ResponseCode.IS_PARTICIPANT_UNDER_4);
        }
        ZonedDateTime start = ZonedDateTime.now();
        ZonedDateTime end = this.exerciseTime.endTime().isBefore(start.plusHours(ExerciseTime.MIN_TIME))
            ? start.plusHours(ExerciseTime.MIN_TIME)
            : this.exerciseTime.endTime();

        this.exerciseStatus = ExerciseStatus.PROGRESS;
        this.exerciseTime = new ExerciseTime(start, end);
    }

    public void progressAuto() {
        if (exerciseStatus.equals(ExerciseStatus.PROGRESS) || exerciseStatus.equals(ExerciseStatus.COMPLETE)) {
            return;
        }
        if (currentParticipant.number() < ParticipantNumber.MIN_PARTICIPANT) {
            return;
        }
        ZonedDateTime start = ZonedDateTime.now();
        ZonedDateTime end = this.exerciseTime.endTime().isBefore(start.plusHours(ExerciseTime.MIN_TIME))
            ? start.plusHours(ExerciseTime.MIN_TIME)
            : this.exerciseTime.endTime();

        this.exerciseStatus = ExerciseStatus.PROGRESS;
        this.exerciseTime = new ExerciseTime(start, end);
    }


    public void complete() {
        this.exerciseStatus = ExerciseStatus.COMPLETE;
        this.exerciseTime = new ExerciseTime(this.exerciseTime.startTime(), ZonedDateTime.now());
    }

    public void completeAuto() {
        if (exerciseTime.endTime().isAfter(ZonedDateTime.now().minusHours(2))) {
            return;
        }
        this.exerciseStatus = ExerciseStatus.COMPLETE;
    }

    private boolean isOverParticipantLimit() {
        return currentParticipant.number() > participantLimit.number();
    }

    private int countAvailableParticipantNumber() {
        return participantLimit.number() - currentParticipant.number();
    }

    private void updateCurrentParticipant() {
        this.currentParticipant = new ParticipantNumber(exerciseUsers.countCurrentParticipateMember());
    }
}
