package hotil.baemo.domains.notification.application.port.input;

import java.util.List;

import org.springframework.stereotype.Service;

import hotil.baemo.domains.chat.domain.value.message.ChatContent;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomName;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import hotil.baemo.domains.notification.application.port.output.MessagingOutPort;
import hotil.baemo.domains.notification.application.port.output.QueryChatOutPort;
import hotil.baemo.domains.notification.application.port.output.QueryDeviceOutPort;
import hotil.baemo.domains.notification.application.usecase.NotifyChatMessageUseCase;
import hotil.baemo.domains.notification.domains.aggregate.Notification;
import hotil.baemo.domains.notification.domains.spec.chat.ChatNotificationSpecification;
import hotil.baemo.domains.notification.domains.value.notification.DeviceToken;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotifyChatInputPort implements NotifyChatMessageUseCase {
	private final QueryDeviceOutPort queryDeviceOutPort;
	private final MessagingOutPort messagingOutPort;
	private final QueryChatOutPort queryChatOutPort;


	@Override
	public void notifyCreationToUsers(ChatRoomId chatRoomId, ChatContent chatMessage, UserId userId) {
		List<DeviceToken> deviceTokens = queryDeviceOutPort.getChatUsersDeviceTokens(chatRoomId);
		ChatRoomName roomName = queryChatOutPort.getChatRoomName(chatRoomId,userId);
		Notification notification = ChatNotificationSpecification.chatMessageCreated(deviceTokens, roomName,
			chatMessage);
		messagingOutPort.sendMessage(notification);
	}
}
