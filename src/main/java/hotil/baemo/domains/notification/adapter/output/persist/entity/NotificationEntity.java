package hotil.baemo.domains.notification.adapter.output.persist.entity;


import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import hotil.baemo.domains.notification.adapter.output.persist.entity.converter.DeviceTokensConverter;
import hotil.baemo.domains.notification.domains.value.notification.NotificationDomain;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "tb_notification")
@NoArgsConstructor
public class NotificationEntity extends BaeMoBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    @Convert(converter = DeviceTokensConverter.class)
    private List<String> deviceTokens = new ArrayList<>();
    private String title;
    private String body;
    @Enumerated(value = EnumType.STRING)
    private NotificationDomain domain;
    private Long domainId;
    private boolean isRead;

    @Builder
    private NotificationEntity(Long id, Long userId, List<String> deviceTokens, String title, String body, NotificationDomain domain,Long domainId, boolean isRead) {
        this.id = id;
        this.userId = userId;
        this.deviceTokens = deviceTokens;
        this.title = title;
        this.body = body;
        this.domain = domain;
        this.domainId = domainId;
        this.isRead = isRead;
    }

    public void isRead() {
        this.isRead = true;
    }
}
