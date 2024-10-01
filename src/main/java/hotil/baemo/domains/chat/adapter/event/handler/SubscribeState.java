package hotil.baemo.domains.chat.adapter.event.handler;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;

//유저는 존재 o , 구독 상태가 x
@RequiredArgsConstructor
public class SubscribeState implements ChatRoomSubscribeState {
	private final ChatRoomSubscribeHandler handler;
	private final ChatRoomId chatRoomId;
	private final UserId userId;
	@Override
	public void handle() {
		handler.updateChatRoomStatus(chatRoomId, userId, ChatRoomUserStatus.SUBSCRIBE);
		handler.handleUnreadMessages(chatRoomId, userId);
	}
}
