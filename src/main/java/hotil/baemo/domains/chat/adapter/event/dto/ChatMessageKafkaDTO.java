package hotil.baemo.domains.chat.adapter.event.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageKafkaDTO {
	private String content;
	private Long userId;
	private String roomId;
}
