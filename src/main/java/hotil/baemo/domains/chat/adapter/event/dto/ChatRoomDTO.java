package hotil.baemo.domains.chat.adapter.event.dto;

public interface ChatRoomDTO {
	record CreateChatRoomDTO(
		String chatRoomId,
		boolean isNewChatRoom
	) implements ChatRoomDTO { }
}