package hotil.baemo.domains.notification.application.dto;

import hotil.baemo.domains.notification.domains.value.notification.NotificationDomain;
import lombok.Builder;

import java.time.Instant;


public interface QNotificationDTO {
    @Builder
    record NotificationList(
        Long id,
        String title,
        String body,
        boolean isRead,
        NotificationDomain domain,
        Long domainId,
        Instant createdAt
    ) implements QNotificationDTO {
    }
}