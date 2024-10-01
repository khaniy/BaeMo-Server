package hotil.baemo.domains.chat.application.ports.output.postgres;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hotil.baemo.domains.chat.adapter.event.dto.ChatMessageKafkaDTO;
import hotil.baemo.domains.chat.adapter.output.postgres.entity.ChatMessageEntity;
import hotil.baemo.domains.chat.adapter.output.postgres.mapper.ChatMessageEntityMapper;
import hotil.baemo.domains.chat.adapter.output.repository.memory.ChatRedisRepository;
import hotil.baemo.domains.chat.application.ports.output.port.ChatMessageOutPort;
import hotil.baemo.domains.chat.adapter.output.repository.ChatMessageRepository;
import hotil.baemo.domains.chat.application.usecase.command.message.CreateChatMessageUseCase;
import hotil.baemo.domains.chat.domain.value.message.ChatMessageId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessagePostgresAdapter implements ChatMessageOutPort {
	private final ChatMessageRepository chatMessageRepository;
	private final ChatMessageEntityMapper chatMessageEntityMapper;
	private final CreateChatMessageUseCase createChatMessageUseCase;
	private final ChatRedisRepository chatRedisRepository;

	@Override
	public ChatMessageId saveChatMessage(ChatMessageKafkaDTO chatMessage, String roomId) {
		ChatMessageEntity chatMessageEntity = chatMessageRepository.save(chatMessageEntityMapper.
			toEntity(chatMessage, roomId));
		createChatMessageUseCase.createUnreadMessage(roomId, chatMessageEntity);
		return new ChatMessageId(chatMessageEntity.getChatMessageId());
	}

	@Override
	@Transactional
	public void markUnreadMessagesAsRead(ChatRoomId chatRoomId, UserId userId, int unreadCount) {
		// Redis에서 해당 사용자의 안읽은 메시지 ID 목록
		Set<String> unreadMessageIds = chatRedisRepository.getUnreadMessageIdsForUser(chatRoomId.id(), userId.id());

		List<Long> messageIdsToUpdate = unreadMessageIds.stream()
			.map(Long::parseLong)
			.limit(unreadCount)
			.toList();

		if (!messageIdsToUpdate.isEmpty()) {
			// unReadCount가 0보다 큰 메시지만 찾기
			List<Long> validMessageIds = chatMessageRepository.findMessagesWithPositiveUnreadCount(messageIdsToUpdate);
			// 벌크 업데이트
			int updatedCount = chatMessageRepository.decrementUnreadCountForMessages(validMessageIds);
			// Redis에서 해당 메시지들의 안읽은 사용자 목록에서 현재 사용자를 제거
			chatRedisRepository.removeUserFromUnreadMessages(chatRoomId.id(), validMessageIds, userId.id());

			// 사용자의 총 안읽은 메시지 수를 갱신
			chatRedisRepository.decrementUnreadCountForUser(chatRoomId.id(), userId.id(), updatedCount);
		}
	}
}