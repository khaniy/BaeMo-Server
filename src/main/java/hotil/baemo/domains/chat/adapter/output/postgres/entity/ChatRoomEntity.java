package hotil.baemo.domains.chat.adapter.output.postgres.entity;


import hotil.baemo.domains.chat.domain.value.room.ChatRoomType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "tb_chat_room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomEntity {
	@Id
	private String chatRoomId;
	@Enumerated(EnumType.STRING)
	private ChatRoomType chatRoomType;

	@Builder
	public ChatRoomEntity(String chatRoomId, ChatRoomType chatRoomType) {
		this.chatRoomId = chatRoomId;
		this.chatRoomType = chatRoomType;
	}

}