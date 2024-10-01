package hotil.baemo.domains.notification.domains.spec.match;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseLocation;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseTime;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseTitle;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseUserStatus;
import hotil.baemo.domains.notification.domains.value.match.MatchCourtNumber;
import hotil.baemo.domains.notification.domains.value.match.MatchOrder;
import hotil.baemo.domains.notification.domains.value.notification.NotificationBody;
import hotil.baemo.domains.notification.domains.value.user.UserName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;


@AllArgsConstructor(access = AccessLevel.MODULE)
public class MatchNotificationBodySpecification {

    public static NotificationBody matchUpdatedToNext() {
        return new NotificationBody("곧 게임을 시작하니, 코트 주변에서 대기 해주세요!");
    }

    public static NotificationBody matchUpdatedToProgress() {
        return new NotificationBody("준비 후 코트에 참가해주세요.");
    }
}
