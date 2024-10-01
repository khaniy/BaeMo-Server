package hotil.baemo.domains.chat.application.usecase.command;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface UpdateChatRoomUseCase {
	void updateChatRoom(ChatRoomId chatRoomId, UserId userId);
}
