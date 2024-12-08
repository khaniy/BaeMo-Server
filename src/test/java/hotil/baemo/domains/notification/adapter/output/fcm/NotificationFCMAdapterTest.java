package hotil.baemo.domains.notification.adapter.output.fcm;

import hotil.baemo.domains.notification.domains.entity.Notification;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.notification.domains.value.notification.NotificationBody;
import hotil.baemo.domains.notification.domains.value.notification.NotificationCode;
import hotil.baemo.domains.notification.domains.value.notification.NotificationTitle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class NotificationFCMAdapterTest {

    @Autowired
    private NotificationFCMAdapter notificationFCMAdapter;

    @Test
    void 알람_발행에_성공할_것이다() {
//        List<DeviceToken> deviceTokens = Arrays.asList(
//            new DeviceToken("dBT3OQSyTU--impaXz6dTn:APA91bF9OL0Ji3JFUAa-Ftn8JtM64uMzhx-rogpbK0ONfQHlnf5nq6kgiwdegYcjcQWxD62qNYIg9OxJJbTOqpKb1TcPHXS1mF9YYWc_HlC7ZHV0b9_zRog") // 상윤이 android
////            new DeviceToken("em28xemOTkLBnYaEOuCvQy:APA91bF2XwW0ngvnLMxNIH44vtJ6C2UNYJ40nBxKhk7FtLXNHUBp8nPvDQXLAEUrNRiXdd3SDnVJNs0n3t4eiu7zwJvU6Vo2nNiKej7_2MAznuTmJdZvZttD89_OJ8in8JafPXggrc4r") //상윤이 ios
//        );
//
//        NotificationTitle notificationTitle = new NotificationTitle("TEST TITLE");
//        NotificationBody notificationBody = new NotificationBody("TEST BODY");
//        Notification notification = Notification.builder()
//            .deviceTokens(deviceTokens)
//            .title(notificationTitle)
//            .body(notificationBody)
//            .code(NotificationCode.DETAIL_CHAT)
//            .build();
//
//        notificationFCMAdapter.sendMessage(notification);

    }
}