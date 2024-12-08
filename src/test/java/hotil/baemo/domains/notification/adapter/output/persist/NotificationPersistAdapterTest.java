package hotil.baemo.domains.notification.adapter.output.persist;

import hotil.baemo.domains.notification.domains.entity.Notification;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NotificationPersistAdapterTest extends FixtureMonkeyBaseSupport {

    @Autowired
    private NotificationPersistAdapter notificationPersistAdapter;

    @Test
    void saveNotification() {
        Notification notification = monkey.giveMeBuilder(Notification.class)
            .setNull("image")
            .sample();
        notificationPersistAdapter.saveNotification(notification);
    }
}