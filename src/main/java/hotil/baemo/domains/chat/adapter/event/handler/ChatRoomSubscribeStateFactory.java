package hotil.baemo.domains.chat.adapter.event.handler;


import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ChatRoomSubscribeStateFactory {
	public static ChatRoomSubscribeState createState(boolean isUserAlreadySubscribe,boolean isUserExist,
		boolean isUserInChaRoom, ChatRoomId chatRoomId, UserId userId, ChatRoomSubscribeHandler handler) {
		if (isUserExist) {
			if (!isUserAlreadySubscribe && !isUserInChaRoom) {
				return new UnSubscribeState(handler, chatRoomId, userId);
			} else {
				return new SubscribeState(handler, chatRoomId, userId);
			}
		} else{
		return new NewSubscribeState(handler, chatRoomId, userId);
		}
	}
}
