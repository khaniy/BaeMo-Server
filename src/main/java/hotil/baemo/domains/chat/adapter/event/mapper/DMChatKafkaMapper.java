package hotil.baemo.domains.chat.adapter.event.mapper;

import hotil.baemo.domains.chat.adapter.event.dto.DMKafkaDTO;
import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.TargetId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public class DMChatKafkaMapper {

	public static DMKafkaDTO.CreateChatKafkaDTO convert(DMKafkaDTO.CreateChatKafkaDTO dto){
		return new DMKafkaDTO.CreateChatKafkaDTO(dto.userId(), dto.targetId(),dto.chatRole());
	}

	public static DMKafkaDTO.CreateChatKafkaDTO convert(UserId userId, TargetId targetId, ChatRole chatRole){
		return new DMKafkaDTO.CreateChatKafkaDTO(userId.id(),targetId.id(),chatRole.toString());
	}

	public static DMKafkaDTO.DeleteChatKafkaDTO convert(UserId userId, ChatRoomId chatRoomId){
		return new DMKafkaDTO.DeleteChatKafkaDTO(userId.id(),chatRoomId.id());
	}

	public static DMKafkaDTO.DeleteChatKafkaDTO convert(DMKafkaDTO.DeleteChatKafkaDTO dto){
		return new DMKafkaDTO.DeleteChatKafkaDTO(dto.userId(), dto.chatRoomId());
	}
}
