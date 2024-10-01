package hotil.baemo.domains.notification.adapter.output.persist;

import hotil.baemo.domains.notification.adapter.output.persist.entity.NotificationEntity;
import hotil.baemo.domains.notification.adapter.output.persist.repository.DeviceQRepository;
import hotil.baemo.domains.notification.adapter.output.persist.repository.NotificationJpaRepository;
import hotil.baemo.domains.notification.adapter.output.persist.repository.NotificationQRepository;
import hotil.baemo.domains.notification.application.dto.QNotificationDTO;
import hotil.baemo.domains.notification.application.port.output.NotificationOutPort;
import hotil.baemo.domains.notification.domains.aggregate.Notification;
import hotil.baemo.domains.notification.domains.value.notification.NotificationId;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationPersistAdapter implements NotificationOutPort {

    private final NotificationQRepository notificationQRepository;
    private final NotificationJpaRepository notificationJpaRepository;
    private final DeviceQRepository deviceQRepository;

    @Override
    public List<QNotificationDTO.NotificationList> getMyNotifications(UserId userId, Pageable pageable, boolean isRead) {
        return notificationQRepository.findMyNotification(userId.id(), pageable, isRead);
    }

    @Override
    public List<QNotificationDTO.NotificationList> getMyNotifications(UserId userId) {
        return notificationQRepository.findMyNotification(userId.id());
    }

    @Override
    public List<QNotificationDTO.NotificationList> getMyNotifications(UserId userId, Pageable pageable) {
        return notificationQRepository.findMyNotification(userId.id(), pageable);
    }

    @Override
    public void updateNotificationsRead(UserId userId, List<NotificationId> notificationIds) {
        List<NotificationEntity> notifications = notificationQRepository.findNotifications(notificationIds.stream().map(NotificationId::id).toList());
        notifications.forEach(NotificationEntity::isRead);
    }

    @Override
    public void updateNotificationsRead(UserId userId) {
        List<NotificationEntity> entities = notificationQRepository.findNotificationsByUserId(userId.id());
        entities.forEach(NotificationEntity::isRead);
    }

    @Override
    public void saveNotification(Notification notification) {
        List<NotificationEntity> entities = deviceQRepository.mapToNotificationEntity(notification);
        notificationJpaRepository.saveAll(entities);
    }
}
