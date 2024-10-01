package hotil.baemo.domains.chat.adapter.output.postgres.repository;

import java.util.List;

import hotil.baemo.domains.chat.adapter.input.rest.dto.ChatMessageDto;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface QueryChatMessageRepository {
	ChatMessageDto.ChatRoomInfoDto loadChatRoom(ChatRoomId chatRoomId);
	ChatMessageDto.UserInfoDto loadUserInfo(ChatRoomId chatRoomId, UserId userId);
	List<ChatMessageDto.UserMessageInfoDto> loadMessagesWithUserInfo(ChatRoomId chatRoomId,UserId userId);
}
