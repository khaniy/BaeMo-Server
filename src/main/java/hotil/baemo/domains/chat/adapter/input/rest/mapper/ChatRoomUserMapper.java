package hotil.baemo.domains.chat.adapter.input.rest.mapper;

import hotil.baemo.domains.chat.adapter.output.postgres.entity.ChatRoomUserEntity;
import hotil.baemo.domains.chat.domain.chat.ChatRoomUser;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomUserMapper {
	public static ChatRoomUser convert(UserId userId,ChatRoomId chatRoomId, ChatRoomUserStatus chatRoomUserStatus){
		return ChatRoomUser.builder()
			.chatRoomId(chatRoomId)
			.userId(userId)
			.chatRoomUserStatus(chatRoomUserStatus)
			.build();
	}

	public static ChatRoomUser convert(ChatRoomUserEntity entity) {
		return ChatRoomUser.builder()
			.chatRoomId(new ChatRoomId(entity.getChatRoomId()))
			.userId(new UserId(entity.getUserId()))
			.chatRoomUserStatus(entity.getChatRoomUserStatus())
			.build();
	}
}
