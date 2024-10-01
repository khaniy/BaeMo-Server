package hotil.baemo.domains.chat.domain.specification;

import hotil.baemo.domains.chat.domain.chat.ChatRoomUser;
import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ChatRoomUserSpecification {
	public static ChatRoomUserSpecification spec(){
		return new ChatRoomUserSpecification();
	}

	public ChatRoomUser createChatRoomUser(UserId userId,ChatRoomId chatRoomId, ChatRole chatRole){
		return ChatRoomUser.builder()
			.chatRoomId(chatRoomId)
			.chatRoomUserStatus(ChatRoomUserStatus.CONNECTED)
			.userId(userId)
			.chatRole(chatRole)
			.build();
	}
}