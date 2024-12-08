package hotil.baemo.domains.notification.adapter.output.persist.repository;

import hotil.baemo.domains.notification.adapter.output.persist.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<NotificationEntity, Long> {

    List<NotificationEntity> findAllByIdIn(List<Long> notificationIds);

    List<NotificationEntity> findAllByUserId(Long userId);
}
