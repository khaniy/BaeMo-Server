package hotil.baemo.domains.chat.domain.chat;


import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoom {
	private final ChatRoomId chatRoomId;
	private final ChatRoomType chatRoomType;


	@Builder
	public ChatRoom(ChatRoomId chatRoomId, ChatRoomType chatRoomType) {
		this.chatRoomId = chatRoomId;
		this.chatRoomType = chatRoomType;
	}
}
