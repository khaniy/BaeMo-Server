package hotil.baemo.domains.chat.application.usecase.command.dm;

import hotil.baemo.domains.chat.adapter.event.dto.ChatRoomDTO;
import hotil.baemo.domains.chat.domain.value.room.TargetId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface CreateDMChatUseCase {
	ChatRoomDTO.CreateChatRoomDTO createDMChatRoom(UserId userId, TargetId targetId);
}

