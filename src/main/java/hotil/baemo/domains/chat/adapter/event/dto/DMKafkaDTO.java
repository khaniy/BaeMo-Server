package hotil.baemo.domains.chat.adapter.event.dto;


public interface DMKafkaDTO {
	record CreateChatKafkaDTO(
		Long userId,
		Long targetId,
		String chatRole
	) implements DMKafkaDTO { }

	record DeleteChatKafkaDTO(
		Long userId,
		String chatRoomId
	) implements DMKafkaDTO { }
}

