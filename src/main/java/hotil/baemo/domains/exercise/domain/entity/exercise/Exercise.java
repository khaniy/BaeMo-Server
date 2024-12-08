package hotil.baemo.domains.exercise.domain.entity.exercise;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUsers;
import hotil.baemo.domains.exercise.domain.specification.ExerciseUserSpec;
import hotil.baemo.domains.exercise.domain.value.ExerciseVOGroup;
import hotil.baemo.domains.exercise.domain.value.exercise.*;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Exercise {
    protected final ExerciseId exerciseId;
    protected final ExerciseType exerciseType;

    protected ExerciseStatus exerciseStatus;
    protected ExerciseTime exerciseTime;
    protected ExerciseUsers exerciseUsers;

    protected Title title;
    protected Description description;
    protected ParticipantNumber participantLimit;
    protected ParticipantNumber currentParticipant;

    protected Location location;
    protected Address address;
    protected LocationCode locationCode;
    protected Coordinate coordinate;


    protected ExerciseThumbnailUrl thumbnailUrl;
    protected boolean isDel;

    public static Exercise of(UserId userId, ExerciseVOGroup voGroup) {
        ExerciseUser exerciseUser = ExerciseUserSpec.of(userId).participantAdmin();
        return Exercise.builder()
            .exerciseType(ExerciseType.IMPROMPTU)
            .title(voGroup.title())
            .description(voGroup.description())
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

    public void update(ExerciseVOGroup voGroup) {
        if (exerciseStatus.equals(ExerciseStatus.COMPLETE)) {
            throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_EXERCISE);
        }
        if (currentParticipant.number() > participantLimit.number()) {
            throw new CustomException(ResponseCode.UNACCEPTABLE_PARTICIPANT_LIMIT);
        }
        this.title = voGroup.title();
        this.description = voGroup.description();
        this.participantLimit = voGroup.participantLimit();
        this.location = voGroup.location();
        this.address = voGroup.address();
        this.locationCode = voGroup.locationCode();
        this.coordinate = voGroup.coordinate();
        this.exerciseTime = voGroup.exerciseTime();
    }

    public void updateThumbnail(ExerciseThumbnailUrl thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public ExerciseUser applyParticipate(UserId userId) {
        ExerciseUser exerciseUser = ExerciseUserSpec.of(userId).pendingMember();
        return addUser(exerciseUser);
    }

    public ExerciseUser approvePendingMember(UserId targetUserId) {
        if (exerciseStatus.equals(ExerciseStatus.COMPLETE)) {
            throw new CustomException(ResponseCode.NOT_ALLOWED_UPDATE_EXERCISE);
        }
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

    public ExerciseUser expelMember(UserId targetUserId) {
        ExerciseUser targetUser = exerciseUsers.pop(targetUserId);
        exerciseUsers.updateWaitingMemberToParticipate(countAvailableParticipantNumber());
        setCurrentParticipant();
        if (hasNoParticipants()) {
            delete();
        } else {
            if (hasNoAdmin()) {
                exerciseUsers.updateAdminToParticipateMember();
            }
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
        if (!targetUser.isAdmin()) {
            throw new CustomException(ResponseCode.IS_NOT_EXERCISE_ADMIN);
        }
        targetUser.toMember();
        return targetUser;
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

        this.exerciseStatus = ExerciseStatus.PROGRESS;
    }

    public void complete() {
        this.exerciseStatus = ExerciseStatus.COMPLETE;
    }

    protected ExerciseUser addUser(ExerciseUser exerciseUser) {
        if (exerciseUser.isParticipateUser() && isOverParticipantLimit()) {
            exerciseUser.toWaitingUser();
        }
        exerciseUsers.add(exerciseUser);
        setCurrentParticipant();
        return exerciseUser;
    }

    protected Boolean hasNoParticipants() {
        return exerciseUsers.countCurrentParticipateMember() == 0;
    }

    protected boolean hasNoAdmin() {
        return exerciseUsers.countCurrentParticipateAdmin() == 0;
    }

    protected boolean isOverParticipantLimit() {
        return currentParticipant.number() >= participantLimit.number();
    }

    protected int countAvailableParticipantNumber() {
        return participantLimit.number() - exerciseUsers.countCurrentParticipateMember();
    }

    protected void setCurrentParticipant() {
        this.currentParticipant = new ParticipantNumber(exerciseUsers.countCurrentParticipateMember());
        if (!exerciseStatus.equals(ExerciseStatus.PROGRESS)){
            this.exerciseStatus = isOverParticipantLimit()
                ? ExerciseStatus.RECRUITMENT_FINISHED
                : ExerciseStatus.RECRUITING;
        }
    }
}
