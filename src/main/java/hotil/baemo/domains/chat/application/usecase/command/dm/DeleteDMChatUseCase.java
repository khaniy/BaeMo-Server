package hotil.baemo.domains.chat.application.usecase.command.dm;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface DeleteDMChatUseCase {
	void deleteDMChatRoom(ChatRoomId chatRoomId,UserId userId);
}
