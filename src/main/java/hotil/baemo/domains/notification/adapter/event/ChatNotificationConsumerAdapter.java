package hotil.baemo.domains.notification.adapter.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import hotil.baemo.config.kafka.KafkaProperties;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.chat.domain.value.message.ChatContent;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import hotil.baemo.domains.notification.adapter.event.dto.ChatMessageEventDTO;
import hotil.baemo.domains.notification.application.usecase.NotifyChatMessageUseCase;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatNotificationConsumerAdapter {
	private final NotifyChatMessageUseCase notifyChatMessageUseCase;

	@KafkaListener(topics = KafkaProperties.CHAT_MESSAGE_TOPIC, groupId = KafkaProperties.NOTIFICATION_STATIC_GROUP_ID)
	public void chatMessageCreated(String message) {
		ChatMessageEventDTO.Created dto = BaeMoObjectUtil.readValue(message, ChatMessageEventDTO.Created.class);
			notifyChatMessageUseCase.notifyCreationToUsers(
				new ChatRoomId(dto.roomId()),
				new ChatContent(dto.content()),
				new UserId(dto.userId())
			);
		}
	}

