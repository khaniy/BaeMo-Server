package hotil.baemo.domains.notification.application.port.output;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomName;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface QueryChatOutPort {
	ChatRoomName getChatRoomName(ChatRoomId chatRoomId, UserId userId);
}
