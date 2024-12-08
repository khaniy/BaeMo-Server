package hotil.baemo.domains.notification.application.dto;

import hotil.baemo.domains.notification.domains.value.notification.NotificationCode;
import lombok.Builder;

import java.time.Instant;


public interface QNotificationDTO {
    @Builder
    record NotificationList(
        Long id,
        String title,
        String body,
        boolean isRead,
        NotificationCode code,
        String data,
        Instant createdAt
    ) implements QNotificationDTO {
    }
}