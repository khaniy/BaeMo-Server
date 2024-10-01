package hotil.baemo.domains.chat.domain.specification;

import hotil.baemo.domains.chat.domain.chat.ChatRoom;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ChatRoomSpecification {
	public static ChatRoomSpecification spec(){
		return new ChatRoomSpecification();
	}

	public ChatRoom createChatRoom(ChatRoomId chatRoomId, ChatRoomType chatRoomType){
		return ChatRoom.builder()
			.chatRoomId(chatRoomId)
			.chatRoomType(chatRoomType)
			.build();
	}
}
