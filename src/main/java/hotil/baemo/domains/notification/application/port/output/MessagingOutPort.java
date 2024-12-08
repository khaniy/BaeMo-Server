package hotil.baemo.domains.notification.application.port.output;

import hotil.baemo.domains.notification.domains.entity.Notification;

public interface MessagingOutPort {

    void sendMessage(Notification notification);
}
