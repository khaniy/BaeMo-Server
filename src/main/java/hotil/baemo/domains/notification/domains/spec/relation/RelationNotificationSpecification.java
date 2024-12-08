package hotil.baemo.domains.notification.domains.spec.relation;

import java.util.List;

import hotil.baemo.domains.chat.domain.value.message.ChatContent;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomName;
import hotil.baemo.domains.notification.domains.aggregate.NotificationData;
import hotil.baemo.domains.notification.domains.entity.Notification;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import hotil.baemo.domains.notification.domains.value.notification.NotificationBody;
import hotil.baemo.domains.notification.domains.value.notification.NotificationCode;
import hotil.baemo.domains.notification.domains.value.notification.NotificationTitle;
import hotil.baemo.domains.notification.domains.value.user.UserName;

public class RelationNotificationSpecification {

	public static Notification friendRequest(
		List<DeviceToken> deviceTokens,
		UserName userName

	) {
		final var title = "친구 요청을 받았어요.";
		final var body = "["+userName+"]님께서 친구 요청을 보냈어요";
		return Notification.builder()
			.deviceTokens(deviceTokens)
			.title(new NotificationTitle(title))
			.body(new NotificationBody(body))
			.data(NotificationData.builder().build())
			.code(NotificationCode.RELATION)
			.build();
	}

	public static Notification friendRequestApproved(
		List<DeviceToken> deviceTokens,
		UserName userName

	) {
		final var title = "[+"+userName+"]님과 서로 친구가 되었어요.";
		final var body = "["+userName+"]님과 함께 배드민턴 정보를 공유해요.";
		return Notification.builder()
			.deviceTokens(deviceTokens)
			.title(new NotificationTitle(title))
			.body(new NotificationBody(body))
			.data(NotificationData.builder().build())
			.code(NotificationCode.RELATION)
			.build();
	}
}
