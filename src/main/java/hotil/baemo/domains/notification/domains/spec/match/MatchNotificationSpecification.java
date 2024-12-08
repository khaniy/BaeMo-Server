package hotil.baemo.domains.notification.domains.spec.match;

import hotil.baemo.domains.notification.domains.aggregate.NotificationData;
import hotil.baemo.domains.notification.domains.entity.Notification;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseId;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseTitle;
import hotil.baemo.domains.notification.domains.value.match.MatchCourtNumber;
import hotil.baemo.domains.notification.domains.value.match.MatchId;
import hotil.baemo.domains.notification.domains.value.match.MatchOrder;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.notification.domains.value.notification.NotificationCode;

import java.util.List;

public class MatchNotificationSpecification {

    public static Notification matchUpdatedToNext(
        MatchId matchId, ExerciseId exerciseId, ExerciseTitle exerciseTitle, List<DeviceToken> deviceTokens,
        MatchOrder matchOrder
    ) {

        return MatchNotificationBuilder.matchUpdatedToNext(matchOrder)
            .deviceTokens(deviceTokens)
            .code(NotificationCode.DETAIL_EXERCISE)
            .data(NotificationData.builder()
                .id(String.valueOf(exerciseId.id()))
                .headerTitle(exerciseTitle.title())
                .build())
            .build();
    }

    public static Notification matchUpdatedToProgress(
        MatchId matchId, ExerciseId exerciseId, ExerciseTitle exerciseTitle, List<DeviceToken> deviceTokens,
        MatchCourtNumber matchCourtNumber,
        MatchOrder matchOrder
    ) {
        return MatchNotificationBuilder.matchUpdatedToProgress(matchOrder, matchCourtNumber)
            .deviceTokens(deviceTokens)
            .code(NotificationCode.DETAIL_EXERCISE)
            .data(NotificationData.builder()
                .id(String.valueOf(exerciseId.id()))
                .headerTitle(exerciseTitle.title())
                .build())
            .build();
    }
}
