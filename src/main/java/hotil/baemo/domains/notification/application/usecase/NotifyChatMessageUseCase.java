package hotil.baemo.domains.notification.application.usecase;

import hotil.baemo.domains.chat.domain.value.message.ChatContent;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface NotifyChatMessageUseCase {
	void notifyCreationToUsers(ChatRoomId chatRoomId, ChatContent chatMessage, UserId userId);
}
