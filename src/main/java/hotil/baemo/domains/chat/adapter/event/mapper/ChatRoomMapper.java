package hotil.baemo.domains.chat.adapter.event.mapper;

import hotil.baemo.domains.chat.adapter.event.dto.ChatRoomDTO;

public class ChatRoomMapper {
	public static ChatRoomDTO.CreateChatRoomDTO convert(String chatRoomId, boolean isNewChatRoom){
		return new ChatRoomDTO.CreateChatRoomDTO(chatRoomId,isNewChatRoom);
	}

}
