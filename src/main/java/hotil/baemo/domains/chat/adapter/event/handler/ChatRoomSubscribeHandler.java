package hotil.baemo.domains.chat.adapter.event.handler;

import org.springframework.stereotype.Service;

import hotil.baemo.domains.chat.adapter.output.repository.memory.ChatRedisRepository;
import hotil.baemo.domains.chat.application.ports.input.command.CommandDMChatInputPort;
import hotil.baemo.domains.chat.application.ports.output.port.CommandChatRoomUserOutPort;
import hotil.baemo.domains.chat.application.usecase.command.SubscribeChatUseCase;
import hotil.baemo.domains.chat.application.usecase.command.message.UpdateChatMessageReadCountUseCase;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import hotil.baemo.domains.chat.adapter.output.repository.QueryChatRoomUserRepository;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomSubscribeHandler {
	private final CommandChatRoomUserOutPort chatRoomUserOutPort;
	private final CommandDMChatInputPort commandDMChatInputPort;
	private final ChatRedisRepository chatRedisRepository;
	private final UpdateChatMessageReadCountUseCase updateChatMessageReadCountUseCase;
	private final SubscribeChatUseCase subscribeChatUseCase;
	private final QueryChatRoomUserRepository chatRoomUserRepository;

	public void handleSubscribe(ChatRoomId chatRoomId, UserId userId) {
		boolean isUserAlreadySubscribe = chatRoomUserRepository.isUserUnsubscribed(chatRoomId, userId);
		boolean isUserExist = chatRoomUserRepository.isUserExist(chatRoomId, userId);
		boolean isUserInChaRoom = chatRedisRepository.isUserInChatRoom(chatRoomId, userId);

		ChatRoomSubscribeState state = ChatRoomSubscribeStateFactory.createState(
			isUserAlreadySubscribe,
			isUserExist,
			isUserInChaRoom,
			chatRoomId,
			userId,
			this
		);
		state.handle();
	}

	//구독 정보 redis에 저장
	public void updateChatRoom(ChatRoomId chatRoomId, UserId userId) {
		commandDMChatInputPort.updateChatRoom(chatRoomId,userId);
	}

	//chatRoomStatus 상태 subscribe 변경
	public void updateChatRoomStatus(ChatRoomId chatRoomId, UserId userId, ChatRoomUserStatus status) {
		chatRoomUserOutPort.updateChatRoomStatus(chatRoomId, userId, status);
	}

	//안읽은 메시지 처리
	public void handleUnreadMessages(ChatRoomId chatRoomId, UserId userId) {
		int unreadCount = chatRedisRepository.getUnreadMessageCount(chatRoomId.id(), userId.id());
		updateChatMessageReadCountUseCase.UpdateChatMessageReadCountUseCase(
			chatRoomId,userId, unreadCount);
		chatRedisRepository.clearUnreadMessages(chatRoomId.id(), userId.id());
	}

	//subscribe 상태로 유저 저장
	public void subscribeChatRoom(ChatRoomId chatRoomId, UserId userId) {
		subscribeChatUseCase.subscribeChatRoom(userId,chatRoomId, ChatRoomUserStatus.SUBSCRIBE);
	}
}
