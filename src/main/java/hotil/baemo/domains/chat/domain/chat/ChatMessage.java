package hotil.baemo.domains.chat.domain.chat;

/**
 * message : 메시지 내용
 * sendUserId : 보낸 사람
 * id : 채팅방 id
 * unReadCount : 읽음처리
 * sendTime*/
import hotil.baemo.domains.chat.domain.value.message.ChatContent;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.message.SendDate;
import hotil.baemo.domains.chat.domain.value.message.SendTime;
import hotil.baemo.domains.chat.domain.value.message.SendUserName;
import hotil.baemo.domains.chat.domain.value.user.ThumbNail;
import hotil.baemo.domains.chat.domain.value.message.UnreadCount;
import hotil.baemo.domains.chat.domain.value.message.SenderId;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatMessage  {
	private final ChatRoomId chatRoomId;
	private final SenderId senderId;
	private final ChatContent content;
	private final UnreadCount unreadCount;
	private final ThumbNail thumbNail;
	private final SendTime sendTime;
	private final SendDate sendDate;
	private final SendUserName userName;

	@Builder
	public ChatMessage(ChatRoomId chatRoomId, SenderId senderId,
		ChatContent content, UnreadCount unreadCount, ThumbNail thumbNail,SendTime sendTime,SendDate sendDate,
		SendUserName userName) {
		this.chatRoomId = chatRoomId;
		this.senderId = senderId;
		this.content = content;
		this.unreadCount=unreadCount;
		this.thumbNail=thumbNail;
		this.sendTime=sendTime;
		this.sendDate=sendDate;
		this.userName=userName;
	}
}