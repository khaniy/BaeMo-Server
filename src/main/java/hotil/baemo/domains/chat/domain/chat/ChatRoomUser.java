package hotil.baemo.domains.chat.domain.chat;

import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomUser {
	private final ChatRoomId chatRoomId;
	private final UserId userId;
	private final ChatRoomUserStatus chatRoomUserStatus;
	private final ChatRole chatRole;


	@Builder
	public ChatRoomUser(ChatRoomId chatRoomId, UserId userId, ChatRoomUserStatus chatRoomUserStatus,ChatRole chatRole) {
		this.chatRoomId = chatRoomId;
		this.userId = userId;
		this.chatRoomUserStatus = chatRoomUserStatus;
		this.chatRole=chatRole;
	}
}
