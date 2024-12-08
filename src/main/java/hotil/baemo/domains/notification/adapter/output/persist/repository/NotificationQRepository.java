package hotil.baemo.domains.notification.adapter.output.persist.repository;

import com.querydsl.core.types.FactoryExpressionBase;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.notification.adapter.output.persist.entity.NotificationEntity;
import hotil.baemo.domains.notification.adapter.output.persist.entity.QNotificationEntity;
import hotil.baemo.domains.notification.application.dto.QNotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationQRepository {

    private final JPAQueryFactory queryFactory;
    private static final QNotificationEntity NOTIFICATION = QNotificationEntity.notificationEntity;

    public List<QNotificationDTO.NotificationList> findMyNotification(Long userId, Pageable pageable, boolean isRead) {
        return queryFactory.select(Projections.constructor(QNotificationDTO.NotificationList.class,
                NOTIFICATION.id,
                NOTIFICATION.title,
                NOTIFICATION.body,
                NOTIFICATION.isRead,
                NOTIFICATION.code,
                NOTIFICATION.domainInfo,
                NOTIFICATION.createdAt)
            )
            .from(NOTIFICATION)
            .where(NOTIFICATION.userId.eq(userId)
                .and(NOTIFICATION.isRead.eq(isRead))
            )
            .orderBy(NOTIFICATION.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    public List<QNotificationDTO.NotificationList> findMyNotification(Long userId, Pageable pageable) {
        return queryFactory.select(Projections.constructor(QNotificationDTO.NotificationList.class,
                NOTIFICATION.id,
                NOTIFICATION.title,
                NOTIFICATION.body,
                NOTIFICATION.isRead,
                NOTIFICATION.code,
                NOTIFICATION.domainInfo,
                NOTIFICATION.createdAt)
            )
            .from(NOTIFICATION)
            .where(NOTIFICATION.userId.eq(userId))
            .orderBy(NOTIFICATION.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    public List<QNotificationDTO.NotificationList> findMyNotification(Long userId) {
        return queryFactory.select(Projections.constructor(QNotificationDTO.NotificationList.class,
                NOTIFICATION.id,
                NOTIFICATION.title,
                NOTIFICATION.body,
                NOTIFICATION.isRead,
                NOTIFICATION.code,
                NOTIFICATION.domainInfo,
                NOTIFICATION.createdAt)
            )
            .from(NOTIFICATION)
            .where(NOTIFICATION.userId.eq(userId)
            )
            .orderBy(NOTIFICATION.createdAt.desc())
            .fetch();
    }
}
