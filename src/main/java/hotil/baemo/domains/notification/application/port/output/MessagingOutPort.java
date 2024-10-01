package hotil.baemo.domains.notification.application.port.output;

import hotil.baemo.domains.notification.domains.aggregate.Notification;

public interface MessagingOutPort {

    void sendMessage(Notification notification);
}
