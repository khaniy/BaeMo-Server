package hotil.baemo.domains.chat.application.usecase.command;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface DeleteChatRoomUseCase {
	void deleteDMChatRoom(ChatRoomId chatRoomId, UserId userId);
}
