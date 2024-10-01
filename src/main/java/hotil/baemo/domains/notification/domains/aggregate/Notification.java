package hotil.baemo.domains.notification.domains.aggregate;

import hotil.baemo.domains.notification.domains.value.notification.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Notification {

    private final NotificationId id;

    private List<DeviceToken> deviceTokens;
    private final NotificationDomain domain;
    private final DomainId domainId;
    private final NotificationTitle title;
    private final NotificationBody body;
    private final NotificationImage image;

    @Builder
    private Notification(NotificationId id, List<DeviceToken> deviceTokens, NotificationDomain domain, DomainId domainId, NotificationTitle title, NotificationBody body, NotificationImage image) {
        this.id = id;
        this.deviceTokens = deviceTokens;
        this.domain = domain;
        this.domainId = domainId;
        this.title = title;
        this.body = body;
        this.image = image;
    }

    // 실패한 토큰을 제거한 새로운 Notification 생성 메서드
    public void removeFailedTokens(List<String> failedTokens) {
        this.deviceTokens = this.deviceTokens.stream()
            .filter(deviceToken -> !failedTokens.contains(deviceToken.token())) // 실패한 토큰 제거
            .collect(Collectors.toList());
    }

}
