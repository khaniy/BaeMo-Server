package hotil.baemo.domains.notification.domains.spec.chat;

import java.util.List;

import hotil.baemo.domains.chat.domain.value.message.ChatContent;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomName;
import hotil.baemo.domains.notification.domains.aggregate.Notification;
import hotil.baemo.domains.notification.domains.value.notification.*;

public class ChatNotificationSpecification {
	public static Notification chatMessageCreated(
		List<DeviceToken> deviceTokens,
		ChatRoomName chatRoomName,
		ChatContent chatContent
	) {
		final var title = chatRoomName.chatRoomName();
		final var body = chatContent.content();
		return Notification.builder()
			.deviceTokens(deviceTokens)
			.title(new NotificationTitle(title))
			.body(new NotificationBody(body))
			.domain(NotificationDomain.CHAT)
			.build();
	}
}
