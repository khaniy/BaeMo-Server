package hotil.baemo.domains.notification.domains.spec.exercise;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseLocation;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseTime;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseTitle;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseUserStatus;
import hotil.baemo.domains.notification.domains.value.notification.NotificationBody;
import hotil.baemo.domains.notification.domains.value.user.UserName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.format.DateTimeFormatter;


@AllArgsConstructor(access = AccessLevel.MODULE)
public class ExerciseNotificationBodySpecification {

    static NotificationBody exerciseCreated(ExerciseTitle exerciseTitle, ExerciseLocation exerciseLocation, ExerciseTime exerciseTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("어서 늦기전에 ").append(exerciseTitle.title()).append(" 에 참여하세요!").append(System.lineSeparator());
        sb.append("시간: ").append(exerciseTime.startTime().format(DateTimeFormatter.ofPattern("MM월 dd일 HH시 mm분"))).append(System.lineSeparator());
        sb.append("장소: ").append(exerciseLocation.location());
        return new NotificationBody(sb.toString());
    }

    static NotificationBody exerciseDeleted() {
        return new NotificationBody("아쉽게도 운동이 취소 되었어요. 새로운 운동을 찾아보세요!");
    }

    static NotificationBody exerciseUserParticipated(UserName userName) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(userName.name()).append("]님께서 운동에 참가하셨어요.");
        return new NotificationBody(sb.toString());
    }

    static NotificationBody exerciseParticipationApplied(UserName userName) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(userName.name()).append("]님께서 참가 신청을 보냈어요.");
        return new NotificationBody(sb.toString());
    }

    static NotificationBody exerciseGuestApplied(UserName userName, UserName guestName) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(userName.name()).append("]님께서 ")
            .append("[").append(guestName.name()).append("]님을 게스트로 참가 신청을 보냈어요.");
        return new NotificationBody(sb.toString());
    }

    static NotificationBody exerciseUserApproved(ExerciseUserStatus exerciseUserStatus) {
        return switch (exerciseUserStatus) {
            case WAITING -> new NotificationBody("현재 인원이 가득차서 대기 하셔야 해요.");
            case PARTICIPATE -> new NotificationBody("운동 시간에 늦지 않게 준비해주세요!");
            default -> throw new CustomException(ResponseCode.NOTIFICATION_POLICY_VIOLATED);
        };
    }

    static NotificationBody exerciseUserCancelled(UserName userName) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(userName.name()).append("]님께서 운동을 떠나셨어요.");
        return new NotificationBody(sb.toString());
    }

    static NotificationBody exerciseUserExpelled(ExerciseTitle title, UserName userName) {
        StringBuilder sb = new StringBuilder();
        sb.append(title.title()).append("에서 ")
            .append("[").append(userName.name()).append("]님을 방출 했어요.");
        return new NotificationBody(sb.toString());
    }
}
