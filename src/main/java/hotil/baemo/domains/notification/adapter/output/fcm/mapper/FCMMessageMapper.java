package hotil.baemo.domains.notification.adapter.output.fcm.mapper;

import com.google.firebase.messaging.MulticastMessage;
import hotil.baemo.domains.notification.domains.entity.Notification;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FCMMessageMapper {

    public static MulticastMessage toMulticastMessage(List<DeviceToken> batch, Notification notification) {
        return MulticastMessage.builder()
            .setNotification(buildNotification(notification))
            .putAllData(buildData(notification))
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

    private static Map<String, String> buildData(Notification notification) {
        Map<String, String> data = new HashMap<>();
        data.put("domain", notification.getCode() != null ?  notification.getCode().name() : "null");
        data.put("headerTitle",notification.getData() != null? notification.getData().headerTitle() : "null");
        data.put("id",notification.getData() != null? String.valueOf(notification.getData().id()) : "null");
        return data;
    }
}
