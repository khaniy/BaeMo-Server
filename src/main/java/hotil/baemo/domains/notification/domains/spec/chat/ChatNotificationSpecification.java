package hotil.baemo.domains.notification.domains.spec.chat;

import java.util.List;

import hotil.baemo.domains.chat.domain.value.message.ChatContent;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomName;
import hotil.baemo.domains.notification.domains.aggregate.NotificationData;
import hotil.baemo.domains.notification.domains.entity.Notification;
import hotil.baemo.domains.notification.domains.value.notification.*;

public class ChatNotificationSpecification {
	public static Notification chatMessageCreated(
        List<DeviceToken> deviceTokens,
        ChatRoomId chatRoomId, ChatRoomName chatRoomName,
        ChatContent chatContent
	) {
		final var title = chatRoomName.chatRoomName();
		final var body = chatContent.content();
		return Notification.builder()
			.deviceTokens(deviceTokens)
			.title(new NotificationTitle(title))
			.body(new NotificationBody(body))
			.code(NotificationCode.DETAIL_CHAT)
			.data(NotificationData.builder()
				.id(chatRoomId.id())
				.headerTitle(chatRoomName.chatRoomName())
				.build())
			.build();
	}
}
