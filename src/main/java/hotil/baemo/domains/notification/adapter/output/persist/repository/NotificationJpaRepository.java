package hotil.baemo.domains.notification.adapter.output.persist.repository;

import hotil.baemo.domains.notification.adapter.output.persist.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, Long> {
}
