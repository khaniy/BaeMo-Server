package hotil.baemo.domains.chat.adapter.event.handler;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UnSubscribeState implements ChatRoomSubscribeState {

	private final ChatRoomSubscribeHandler handler;
	private final ChatRoomId chatRoomId;
	private final UserId userId;
	@Override
	public void handle() {
		handler.updateChatRoom(chatRoomId,userId);
	}
}
