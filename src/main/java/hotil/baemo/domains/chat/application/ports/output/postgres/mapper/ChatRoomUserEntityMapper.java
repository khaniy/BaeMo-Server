package hotil.baemo.domains.chat.application.ports.output.postgres.mapper;


import hotil.baemo.domains.chat.adapter.output.postgres.entity.ChatRoomUserEntity;
import hotil.baemo.domains.chat.domain.chat.ChatRoomUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomUserEntityMapper {
	public static ChatRoomUserEntity toEntity(ChatRoomUser chatRoomUser){
		return ChatRoomUserEntity.builder()
			.chatRoomId(chatRoomUser.getChatRoomId().id())
			.userId(chatRoomUser.getUserId().id())
			.chatRoomUserStatus(chatRoomUser.getChatRoomUserStatus())
			.chatRole(chatRoomUser.getChatRole())
			.build();

	}
}
