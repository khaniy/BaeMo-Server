package hotil.baemo.domains.notification.application.port.output;

import hotil.baemo.domains.notification.application.dto.QNotificationDTO;
import hotil.baemo.domains.notification.domains.aggregate.Notification;
import hotil.baemo.domains.notification.domains.value.notification.NotificationId;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationOutPort {

    List<QNotificationDTO.NotificationList> getMyNotifications(UserId userId, Pageable pageable, boolean isRead);

    void updateNotificationsRead(UserId userId, List<NotificationId> notificationIds);

    void updateNotificationsRead(UserId userId);

    void saveNotification(Notification notification);

    List<QNotificationDTO.NotificationList> getMyNotifications(UserId userId);

    List<QNotificationDTO.NotificationList> getMyNotifications(UserId userId, Pageable pageable);

}
