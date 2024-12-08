package hotil.baemo.domains.notification.domains.spec.exercise;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.notification.domains.entity.Notification;
import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseLocation;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseTime;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseTitle;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseUserStatus;
import hotil.baemo.domains.notification.domains.value.notification.NotificationBody;
import hotil.baemo.domains.notification.domains.value.notification.NotificationTitle;
import hotil.baemo.domains.notification.domains.value.user.UserName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor(access = AccessLevel.MODULE)
public class ExerciseNotificationBuilder {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    static Notification.NotificationBuilder exerciseCreated(ClubTitle clubTitle, ExerciseTitle exerciseTitle, ExerciseLocation exerciseLocation, ExerciseTime exerciseTime) {
        return Notification.builder()
            .title(new NotificationTitle(formatMessage("[%s]에서 새로운 운동을 열었어요.", clubTitle.title())))
            .body(new NotificationBody(formatMessage(
                "어서 늦기전에 %s 에 참여하세요!" + LINE_SEPARATOR +
                    "시간: %s" + LINE_SEPARATOR +
                    "장소: %s",
                exerciseTitle.title(),
                exerciseTime.startTime().format(DateTimeFormatter.ofPattern("MM월 dd일 HH시 mm분")),
                exerciseLocation.location())));
    }

    static Notification.NotificationBuilder exerciseDeleted(ExerciseTitle exerciseTitle) {
        return Notification.builder()
            .title(new NotificationTitle(formatMessage("[%s]이 취소 되었어요.", exerciseTitle.title())))
            .body(new NotificationBody("아쉽게도 운동이 취소 되었어요. 새로운 운동을 찾아보세요!"))
            ;
    }

    static Notification.NotificationBuilder exerciseUserParticipated(ExerciseUserStatus exerciseUserStatus, ExerciseTitle exerciseTitle, UserName userName) {
        return Notification.builder()
            .title(new NotificationTitle(formatMessage("[%s]", exerciseTitle.title())))
            .body(new NotificationBody(
                switch (exerciseUserStatus) {
                    case WAITING -> formatMessage("[%s]님께서 대기 중이에요.", userName.name());
                    case PARTICIPATE -> formatMessage("[%s]님께서 운동에 참가하셨어요.", userName.name());
                    default -> throw new CustomException(ResponseCode.NOTIFICATION_POLICY_VIOLATED);
                }))
            ;
    }

    static Notification.NotificationBuilder exerciseParticipationApplied(ExerciseTitle exerciseTitle, UserName userName) {
        return Notification.builder()
            .title(new NotificationTitle(formatMessage("[%s]", exerciseTitle.title())))
            .body(new NotificationBody(formatMessage("[%s]님께서 참가 신청을 보냈어요.", userName.name())))
            ;
    }

    static Notification.NotificationBuilder exerciseGuestApplied(ExerciseTitle exerciseTitle, UserName userName, UserName guestName, boolean toGuest) {
        if (toGuest) {
            return Notification.builder()
                .title(new NotificationTitle(formatMessage("[%s]에 게스트로 신청되었어요.", exerciseTitle.title())))
                .body(new NotificationBody(formatMessage("[%s]님께서 [%s]님을 게스트로 참가 신청을 보냈어요.", userName.name(), guestName.name())));
        }
        return Notification.builder()
            .title(new NotificationTitle(formatMessage("[%s]", exerciseTitle.title())))
            .body(new NotificationBody(formatMessage("[%s]님께서 [%s]님을 게스트로 참가 신청을 보냈어요.", userName.name(), guestName.name())));
    }

    static Notification.NotificationBuilder exerciseUserApproved(ExerciseTitle exerciseTitle, ExerciseUserStatus exerciseUserStatus) {
        return Notification.builder()
            .title(new NotificationTitle(formatMessage("[%s]에서 운동참가를 승인했어요.", exerciseTitle.title())))
            .body(new NotificationBody(
                switch (exerciseUserStatus) {
                    case WAITING -> "현재 인원이 가득차서 대기 하셔야 해요.";
                    case PARTICIPATE -> "운동 시간에 늦지 않게 준비해주세요!";
                    default -> throw new CustomException(ResponseCode.NOTIFICATION_POLICY_VIOLATED);
                }
            ));
    }

    static Notification.NotificationBuilder exerciseUserLeft(ExerciseTitle exerciseTitle, UserName userName) {
        return Notification.builder()
            .title(new NotificationTitle(formatMessage("[%s]", exerciseTitle.title())))
            .body(new NotificationBody(formatMessage("[%s]님께서 운동을 떠나셨어요.", userName.name())));

    }

    static Notification.NotificationBuilder exerciseUserExpelled(ExerciseTitle exerciseTitle, UserName userName) {
        return Notification.builder()
            .title(new NotificationTitle(formatMessage("[%s]", exerciseTitle.title())))
            .body(new NotificationBody(formatMessage("%s에서 [%s]님을 방출 했어요.", exerciseTitle.title(), userName.name())));
    }

    // 공통 문자열 포맷 메서드
    private static String formatMessage(String template, Object... args) {
        return String.format(template, args);
    }
}