package hotil.baemo.domains.notification.application.port.input;

import hotil.baemo.domains.notification.application.port.output.MessagingOutPort;
import hotil.baemo.domains.notification.application.port.output.NotificationOutPort;
import hotil.baemo.domains.notification.application.port.output.QueryDeviceOutPort;
import hotil.baemo.domains.notification.application.usecase.NotifyMatchUseCase;
import hotil.baemo.domains.notification.domains.aggregate.Notification;
import hotil.baemo.domains.notification.domains.spec.match.MatchNotificationSpecification;
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
    private final MessagingOutPort messagingOutPort;
    private final NotificationOutPort notificationOutPort;

    @Override
    public void notifyMatchUpdatedToMatchUser(
        MatchId matchId,
        MatchCourtNumber matchCourtNumber,
        MatchOrder matchOrder,
        List<UserId> matchUserIds,
        MatchStatus matchStatus
    ) {
        List<DeviceToken> deviceTokens = queryDeviceOutPort.getDeviceTokensByUserIds(matchUserIds);
        Notification notification = switch (matchStatus) {
            case PROGRESS ->
                MatchNotificationSpecification.matchUpdatedToProgress(matchId, deviceTokens, matchCourtNumber, matchOrder);
            case NEXT ->
                MatchNotificationSpecification.matchUpdatedToNext(matchId, deviceTokens, matchCourtNumber, matchOrder);
            default -> throw new IllegalStateException("Unexpected value: " + matchStatus);
        };
        messagingOutPort.sendMessage(notification);
//        notificationOutPort.saveNotification(notification);
    }
}
