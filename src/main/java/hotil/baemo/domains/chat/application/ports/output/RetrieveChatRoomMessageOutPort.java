package hotil.baemo.domains.chat.application.ports.output;


import hotil.baemo.domains.chat.adapter.input.rest.dto.ChatMessageDto;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface RetrieveChatRoomMessageOutPort {
	ChatMessageDto.ChatRoomMessage getChatRoomMessage(UserId userId,ChatRoomId chatRoomId);
	// ChatMessageDto.ChatRoomMessage getChatRoomMessage(UserId userId,ChatRoomId chatRoomId,Integer pageSize, Integer pageNumber);
}
