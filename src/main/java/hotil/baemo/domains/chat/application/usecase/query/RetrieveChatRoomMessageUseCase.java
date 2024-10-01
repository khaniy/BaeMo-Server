package hotil.baemo.domains.chat.application.usecase.query;


import hotil.baemo.domains.chat.adapter.input.rest.dto.ChatMessageDto;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface RetrieveChatRoomMessageUseCase {
	ChatMessageDto.ChatRoomMessage retrieveChatRoomMessage(UserId userId,ChatRoomId chatRoomId);
	// ChatMessageDto.ChatRoomMessage retrieveChatRoomMessage(UserId userId,ChatRoomId chatRoomId,Integer pageSize, Integer pageNumber);
}