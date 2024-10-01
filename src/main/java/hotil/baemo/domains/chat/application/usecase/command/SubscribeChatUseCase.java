package hotil.baemo.domains.chat.application.usecase.command;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface SubscribeChatUseCase {
	void subscribeChatRoom(UserId userId, ChatRoomId roomId, ChatRoomUserStatus chatRoomUserStatus);
}
