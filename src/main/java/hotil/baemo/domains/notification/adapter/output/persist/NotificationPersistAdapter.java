package hotil.baemo.domains.notification.adapter.output.persist;

import hotil.baemo.domains.notification.adapter.output.persist.entity.NotificationEntity;
import hotil.baemo.domains.notification.adapter.output.persist.repository.DeviceQRepository;
import hotil.baemo.domains.notification.adapter.output.persist.repository.NotificationQRepository;
import hotil.baemo.domains.notification.adapter.output.persist.repository.NotificationRepository;
import hotil.baemo.domains.notification.application.dto.QNotificationDTO;
import hotil.baemo.domains.notification.application.port.output.NotificationOutPort;
import hotil.baemo.domains.notification.domains.entity.Notification;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.notification.domains.value.notification.NotificationId;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationPersistAdapter implements NotificationOutPort {

    private final NotificationQRepository notificationQRepository;
    private final NotificationRepository notificationRepository;
    private final DeviceQRepository deviceQRepository;

    @Override
    public List<QNotificationDTO.NotificationList> getMyNotifications(UserId userId, Pageable pageable, boolean isRead) {
        return notificationQRepository.findMyNotification(userId.id(), pageable, isRead);
    }

    @Override
    public List<QNotificationDTO.NotificationList> getMyNotifications(UserId userId, Pageable pageable) {
        return notificationQRepository.findMyNotification(userId.id(), pageable);
    }

    @Override
    public void updateNotificationsRead(UserId userId, List<NotificationId> notificationIds) {
        List<NotificationEntity> notifications = notificationRepository.findAllByIdIn(notificationIds.stream().map(NotificationId::id).toList());
        notifications.forEach(NotificationEntity::isRead);
    }

    @Override
    public void updateNotificationsRead(UserId userId) {
        List<NotificationEntity> entities = notificationRepository.findAllByUserId(userId.id());
        entities.forEach(NotificationEntity::isRead);
    }

    @Override
    public void saveNotification(Notification notification) {
        final var tokensByUser = notification.getDeviceTokens().stream()
            .collect(Collectors.groupingBy(
                DeviceToken::userId,
                Collectors.mapping(DeviceToken::token, Collectors.toList())
            ));
        List<NotificationEntity> entities =  tokensByUser.entrySet().stream()
            .map(e -> NotificationEntity.builder()
                .userId(e.getKey())
                .deviceTokens(e.getValue())
                .title(notification.getTitle().title())
                .body(notification.getBody().body())
                .code(notification.getCode())
                .domainInfo(notification.getData().toString())
                .isRead(false)
                .build())
            .collect(Collectors.toList());
        notificationRepository.saveAll(entities);
    }
}
