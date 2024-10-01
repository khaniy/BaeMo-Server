package hotil.baemo.domains.chat.application.ports.output.port;

import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.chat.ChatRoomUser;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface CommandChatRoomUserOutPort {
	void save(ChatRoomUser chatRoomUser);
	Integer getsubscribeChatRoomUser(ChatRoomId chatRoomId);
	void updateChatRoomStatus(ChatRoomId chatRoomId,UserId userId, ChatRoomUserStatus chatRoomUserStatus);
	void updateChatUserRole(ChatRoomId chatRoomId, UserId userId, ChatRole chatRole);
}
