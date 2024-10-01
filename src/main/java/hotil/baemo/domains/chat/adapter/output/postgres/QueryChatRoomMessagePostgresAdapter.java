package hotil.baemo.domains.chat.adapter.output.postgres;


import org.springframework.stereotype.Service;

import hotil.baemo.domains.chat.adapter.input.rest.dto.ChatMessageDto;
import hotil.baemo.domains.chat.adapter.output.postgres.repository.QueryChatMessageRepository;
import hotil.baemo.domains.chat.application.ports.output.RetrieveChatRoomMessageOutPort;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueryChatRoomMessagePostgresAdapter implements RetrieveChatRoomMessageOutPort {
	private final QueryChatMessageRepository chatMessageRepository;
	@Override
	public ChatMessageDto.ChatRoomMessage getChatRoomMessage(UserId userId, ChatRoomId chatRoomId) {
		final var messages = chatMessageRepository.loadMessagesWithUserInfo(chatRoomId, userId);
		final var chatRoomInfo = chatMessageRepository.loadChatRoom(chatRoomId);

		return ChatMessageDto.ChatRoomMessage.builder()
			.roomInfoDto(chatRoomInfo)
			.messages(messages)
			.build();
	}
}
