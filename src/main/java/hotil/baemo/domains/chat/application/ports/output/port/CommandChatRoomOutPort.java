package hotil.baemo.domains.chat.application.ports.output.port;

import hotil.baemo.domains.chat.domain.chat.ChatRoom;
import hotil.baemo.domains.chat.domain.chat.ChatRoomUser;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;

public interface CommandChatRoomOutPort {
	void save(ChatRoom chatRoom);
	void deleteChatRoom(ChatRoom chatRoom);
	void deleteChatRoomUser(ChatRoomUser chatRoomUser);
	boolean existsByChatRoomId(ChatRoomId chatRoomId);
}
