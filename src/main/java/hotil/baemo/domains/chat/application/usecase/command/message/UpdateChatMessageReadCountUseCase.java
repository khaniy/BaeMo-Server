package hotil.baemo.domains.chat.application.usecase.command.message;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface UpdateChatMessageReadCountUseCase {
	void UpdateChatMessageReadCountUseCase(ChatRoomId chatRoomId, UserId userId,int readCount);
}
