package hotil.baemo.domains.chat.application.ports.input.command;

import java.util.List;

import org.springframework.stereotype.Service;

import hotil.baemo.domains.chat.adapter.output.repository.memory.ChatRedisRepository;
import hotil.baemo.domains.chat.adapter.output.postgres.entity.ChatMessageEntity;
import hotil.baemo.domains.chat.adapter.output.postgres.entity.ChatRoomUserEntity;
import hotil.baemo.domains.chat.adapter.output.repository.ChatRoomUserJpaRepository;
import hotil.baemo.domains.chat.application.usecase.command.message.CreateChatMessageUseCase;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageInputPort implements CreateChatMessageUseCase {
	private final ChatRedisRepository chatRedisRepository;
	private final ChatRoomUserJpaRepository chatRoomUserJpaRepository;

	@Override
	public void createUnreadMessage(String chatRoomId, ChatMessageEntity chatMessage) {
		List<ChatRoomUserEntity> chatRoomUsers = chatRoomUserJpaRepository.findByChatRoomId(chatRoomId);
		Long senderId = chatMessage.getUserId();

		List<Long> unreadUserIds = chatRoomUsers.stream()
			.filter(user -> !user.getUserId().equals(senderId) && user.getChatRoomUserStatus() != ChatRoomUserStatus.SUBSCRIBE)
			.map(ChatRoomUserEntity::getUserId)
			.toList();

		if (!unreadUserIds.isEmpty()) {
			chatRedisRepository.addUnreadMessage(chatRoomId, chatMessage.getChatMessageId(), unreadUserIds);
		}
	}
}