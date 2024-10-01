package hotil.baemo.domains.chat.application.ports.output.port;

import hotil.baemo.domains.chat.adapter.event.dto.ChatRoomDTO;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.TargetId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface DMChatRoomOutPort {
	ChatRoomDTO.CreateChatRoomDTO createDMChatRoom(UserId userId, TargetId targetId);
	void deleteDMChatRoom(UserId userId, ChatRoomId chatRoomId);
}
