package hotil.baemo.domains.chat.application.ports.output.port;

import hotil.baemo.domains.chat.adapter.event.dto.ChatMessageKafkaDTO;
import hotil.baemo.domains.chat.domain.value.message.ChatMessageId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface ChatMessageOutPort {
	ChatMessageId saveChatMessage(ChatMessageKafkaDTO chatMessage,String roomId);
	void markUnreadMessagesAsRead(ChatRoomId chatRoomId, UserId userId,int readCount);
}
