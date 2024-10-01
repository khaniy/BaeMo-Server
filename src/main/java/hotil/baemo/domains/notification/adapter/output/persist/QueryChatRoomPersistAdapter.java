package hotil.baemo.domains.notification.adapter.output.persist;

import org.springframework.stereotype.Service;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomName;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import hotil.baemo.domains.notification.adapter.output.persist.repository.ChatRoomQRepository;
import hotil.baemo.domains.notification.application.port.output.QueryChatOutPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueryChatRoomPersistAdapter implements QueryChatOutPort {
	private final ChatRoomQRepository chatRoomQRepository;
	@Override
	public ChatRoomName getChatRoomName(ChatRoomId chatRoomId, UserId userId) {
		String roomName = chatRoomQRepository.findChatRoomName(chatRoomId,userId);
		return new ChatRoomName(roomName);
	}
}


