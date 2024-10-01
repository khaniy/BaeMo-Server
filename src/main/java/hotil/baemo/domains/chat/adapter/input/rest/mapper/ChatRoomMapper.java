package hotil.baemo.domains.chat.adapter.input.rest.mapper;

import hotil.baemo.domains.chat.adapter.output.postgres.entity.ChatRoomEntity;
import hotil.baemo.domains.chat.domain.chat.ChatRoom;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomMapper {
	public static ChatRoom convert (String chatRoomId, ChatRoomType chatRoomType){
		return ChatRoom.builder()
			.chatRoomId(new ChatRoomId(chatRoomId))
			.chatRoomType(chatRoomType)
			.build();
	}

	public static ChatRoomEntity convert(ChatRoom chatRoom) {
		return ChatRoomEntity.builder()
			.chatRoomId(chatRoom.getChatRoomId().id())
			.chatRoomType(chatRoom.getChatRoomType())
			.build();
	}
}
