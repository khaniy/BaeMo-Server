package hotil.baemo.domains.chat.application.usecase.command;

import hotil.baemo.domains.chat.adapter.event.dto.ChatRoomDTO;
import hotil.baemo.domains.chat.domain.value.room.TargetId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface CreateChatRoomUseCase {
	ChatRoomDTO.CreateChatRoomDTO createChatRoom(UserId userId, TargetId targetId);
}
