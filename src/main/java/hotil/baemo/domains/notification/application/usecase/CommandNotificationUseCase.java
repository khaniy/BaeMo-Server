package hotil.baemo.domains.notification.application.usecase;

import hotil.baemo.domains.notification.domains.value.notification.NotificationId;
import hotil.baemo.domains.notification.domains.value.user.UserId;

import java.util.List;

public interface CommandNotificationUseCase {
    void updateNotificationsRead(UserId userId, List<NotificationId> notificationIds);

    void updateAllNotificationRead(UserId userId);
}
