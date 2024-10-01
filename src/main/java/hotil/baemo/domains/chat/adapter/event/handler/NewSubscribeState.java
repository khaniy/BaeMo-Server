package hotil.baemo.domains.chat.adapter.event.handler;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
//새롭게 채팅방 구독하는 상태
@RequiredArgsConstructor
public class NewSubscribeState implements ChatRoomSubscribeState {
	private final ChatRoomSubscribeHandler handler;
	private final ChatRoomId chatRoomId;
	private final UserId userId;

	@Override
	public void handle() {
		handler.updateChatRoom(chatRoomId,userId);
		handler.subscribeChatRoom(chatRoomId,userId);
	}
}
