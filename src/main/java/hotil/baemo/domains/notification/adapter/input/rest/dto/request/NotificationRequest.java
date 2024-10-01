package hotil.baemo.domains.notification.adapter.input.rest.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface NotificationRequest {
    record UpdateNotificationRead(
        @NotNull
        List<Long> notificationIds
    )implements NotificationRequest {}
}
