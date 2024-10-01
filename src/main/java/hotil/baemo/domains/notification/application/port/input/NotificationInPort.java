package hotil.baemo.domains.notification.application.port.input;

import hotil.baemo.domains.notification.application.dto.QNotificationDTO;
import hotil.baemo.domains.notification.application.port.output.*;
import hotil.baemo.domains.notification.application.usecase.CommandNotificationUseCase;
import hotil.baemo.domains.notification.application.usecase.RetrieveNotificationUseCase;
import hotil.baemo.domains.notification.domains.value.notification.NotificationId;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationInPort implements RetrieveNotificationUseCase, CommandNotificationUseCase {

    private final NotificationOutPort notificationOutPort;

    @Override
    public List<QNotificationDTO.NotificationList> retrieveMyNotifications(UserId userId) {
        return notificationOutPort.getMyNotifications(userId);
    }

    @Override
    public List<QNotificationDTO.NotificationList> retrieveMyNotifications(UserId userId, Pageable pageable) {
        return notificationOutPort.getMyNotifications(userId, pageable);
    }

    @Override
    public List<QNotificationDTO.NotificationList> retrieveMyUnReadNotifications(UserId userId, Pageable pageable) {
        return notificationOutPort.getMyNotifications(userId, pageable, false);
    }

    @Override
    @Transactional
    public void updateNotificationsRead(UserId userId, List<NotificationId> notificationIds) {
        notificationOutPort.updateNotificationsRead(userId, notificationIds);
    }

    @Override
    public void updateAllNotificationRead(UserId userId) {
        notificationOutPort.updateNotificationsRead(userId);
    }
}
