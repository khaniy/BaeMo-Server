package hotil.baemo.domains.chat.adapter.output.postgres.entity;

import hotil.baemo.domains.chat.domain.roles.ChatRole;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tb_chat_room_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomUserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String chatRoomId;
	private Long userId;
	@Enumerated(EnumType.STRING)
	private ChatRole chatRole;
	@Enumerated(EnumType.STRING)
	private ChatRoomUserStatus chatRoomUserStatus;

	@Builder
	public ChatRoomUserEntity(Long userId,String chatRoomId, ChatRoomUserStatus chatRoomUserStatus,ChatRole chatRole) {
		this.chatRoomId=chatRoomId;
		this.userId=userId;
		this.chatRoomUserStatus = chatRoomUserStatus;
		this.chatRole=chatRole;
	}

	public void updateChatRoomStatus(ChatRoomUserStatus chatRoomUserStatus){
		this.chatRoomUserStatus = chatRoomUserStatus;
	}

	public void updateChatUserRole(ChatRole chatRole){
		this.chatRole = chatRole;
	}
}