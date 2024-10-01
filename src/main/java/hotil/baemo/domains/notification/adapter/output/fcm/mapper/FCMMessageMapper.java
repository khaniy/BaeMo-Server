package hotil.baemo.domains.notification.adapter.output.fcm.mapper;

import com.google.firebase.messaging.MulticastMessage;
import hotil.baemo.domains.notification.domains.aggregate.Notification;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.notification.domains.value.notification.DomainId;
import hotil.baemo.domains.notification.domains.value.notification.NotificationDomain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FCMMessageMapper {

    public static MulticastMessage toMulticastMessage(List<DeviceToken> batch, Notification notification) {
        return MulticastMessage.builder()
            .setNotification(buildNotification(notification))
            .putAllData(buildData(notification.getDomain(), notification.getDomainId()))
            .addAllTokens(batch.stream().map(DeviceToken::token).toList())
            .build();
    }

    private static com.google.firebase.messaging.Notification buildNotification(Notification notification) {
        return com.google.firebase.messaging.Notification.builder()
            .setTitle(notification.getTitle().title())
            .setBody(notification.getBody().body())
            .setImage(notification.getImage() != null ? notification.getImage().url() : null)
            .build();
    }

    private static Map<String, String> buildData(NotificationDomain domain, DomainId domainId) {
        Map<String, String> data = new HashMap<>();
        data.put("domain", domain!=null ? domain.toString() : "NotExisted");
        data.put("domainId", domainId!=null ? String.valueOf(domainId.id()) : "NotExisted");
        return data;
    }
}
