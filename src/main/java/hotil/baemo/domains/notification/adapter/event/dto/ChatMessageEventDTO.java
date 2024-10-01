package hotil.baemo.domains.notification.adapter.event.dto;

public interface ChatMessageEventDTO {
	record Created(
		 String content,
		 Long userId,
		 String roomId
	) implements ChatMessageEventDTO {
	}
}