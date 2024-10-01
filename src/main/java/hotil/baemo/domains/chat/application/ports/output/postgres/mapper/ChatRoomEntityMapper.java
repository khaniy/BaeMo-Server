package hotil.baemo.domains.chat.application.ports.output.postgres.mapper;

import hotil.baemo.domains.chat.adapter.output.postgres.entity.ChatRoomEntity;
import hotil.baemo.domains.chat.domain.chat.ChatRoom;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomEntityMapper {
	public static ChatRoomEntity toEntity(ChatRoom domain){
		return ChatRoomEntity.builder()
				.chatRoomId(domain.getChatRoomId().id())
				.chatRoomType(domain.getChatRoomType())
				.build();
	}

	public static ChatRoom toDomain(ChatRoomEntity entity){
		return ChatRoom.builder()
			.chatRoomId(new ChatRoomId(entity.getChatRoomId()))
			.chatRoomType(entity.getChatRoomType())
			.build();
	}
}
