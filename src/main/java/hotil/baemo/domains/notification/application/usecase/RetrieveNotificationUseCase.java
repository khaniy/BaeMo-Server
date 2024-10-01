package hotil.baemo.domains.notification.application.usecase;

import hotil.baemo.domains.notification.application.dto.QNotificationDTO;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RetrieveNotificationUseCase {

    List<QNotificationDTO.NotificationList> retrieveMyUnReadNotifications(UserId userId, Pageable pageable);

    List<QNotificationDTO.NotificationList> retrieveMyNotifications(UserId userId);

    List<QNotificationDTO.NotificationList> retrieveMyNotifications(UserId userId, Pageable pageable);
}
