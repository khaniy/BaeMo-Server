package hotil.baemo.domains.notification.application.port.input;

import hotil.baemo.domains.notification.application.port.output.MessagingOutPort;
import hotil.baemo.domains.notification.application.port.output.NotificationOutPort;
import hotil.baemo.domains.notification.application.port.output.QueryDeviceOutPort;
import hotil.baemo.domains.notification.application.port.output.QueryExerciseOutPort;
import hotil.baemo.domains.notification.application.usecase.NotifyMatchUseCase;
import hotil.baemo.domains.notification.domains.entity.Notification;
import hotil.baemo.domains.notification.domains.spec.match.MatchNotificationSpecification;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseId;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseTitle;
import hotil.baemo.domains.notification.domains.value.match.MatchCourtNumber;
import hotil.baemo.domains.notification.domains.value.match.MatchId;
import hotil.baemo.domains.notification.domains.value.match.MatchOrder;
import hotil.baemo.domains.notification.domains.value.match.MatchStatus;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class NotifyMatchInPort implements NotifyMatchUseCase {

    private final QueryDeviceOutPort queryDeviceOutPort;
    private final QueryExerciseOutPort queryExerciseOutPort;
    private final MessagingOutPort messagingOutPort;
    private final NotificationOutPort notificationOutPort;

    @Override
    public void notifyMatchUpdatedToMatchUser(
        MatchId matchId,
        ExerciseId exerciseId,
        MatchCourtNumber matchCourtNumber,
        MatchOrder matchOrder,
        List<UserId> matchUserIds,
        MatchStatus matchStatus
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getUsersDeviceTokens(matchUserIds);
        ExerciseTitle exerciseTitle = queryExerciseOutPort.getExerciseTitle(exerciseId);
        Notification notification = switch (matchStatus) {
            case PROGRESS ->
                MatchNotificationSpecification.matchUpdatedToProgress(matchId,exerciseId,exerciseTitle, deviceTokens, matchCourtNumber, matchOrder);
            case NEXT -> MatchNotificationSpecification.matchUpdatedToNext(matchId, exerciseId,exerciseTitle, deviceTokens, matchOrder);
            default -> null;
        };
        messagingOutPort.sendMessage(notification);
//        notificationOutPort.saveNotification(notification);
    }
}
