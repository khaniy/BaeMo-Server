package hotil.baemo.domains.chat.adapter.output.postgres.entity;



import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "tb_chat_message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageEntity  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chatMessageId;
	private String chatRoomId;
	private String content;
	private Long userId;
	private Integer unreadCount;
	private LocalDateTime createdAt;

	@Builder
	public ChatMessageEntity(Long chatMessageId, String chatRoomId, String content,Long userId,Integer unreadCount, LocalDateTime createdAt) {
		this.chatMessageId = chatMessageId;
		this.chatRoomId = chatRoomId;
		this.content=content;
		this.userId=userId;
		this.unreadCount = (unreadCount == null) ? 0 : unreadCount;
		this.createdAt=createdAt;
	}

	public void updateReadCount(Integer count){
		this.unreadCount =count;
	}
}