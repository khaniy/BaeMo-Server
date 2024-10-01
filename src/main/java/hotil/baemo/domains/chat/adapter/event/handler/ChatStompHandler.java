package hotil.baemo.domains.chat.adapter.event.handler;

import java.security.Principal;
import java.util.Objects;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import hotil.baemo.config.socket.error.HandleCustomException;
import hotil.baemo.config.socket.interceptor.handler.MessagingStompHandler;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;

import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.chat.application.ports.output.port.CommandChatRoomUserOutPort;
import hotil.baemo.domains.chat.application.usecase.command.SubscribeChatUseCase;

import hotil.baemo.domains.chat.application.utils.ChatRoomUtils;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatStompHandler extends MessagingStompHandler {
	private final CommandChatRoomUserOutPort chatRoomUserOutPort;
	private final SubscribeChatUseCase subscribeChatUseCase;
	private final ChatRoomSubscribeHandler handler;

	@Override
	@HandleCustomException
	public void handleMessage(StompHeaderAccessor accessor) {
		Long userId = extractUserAuth(accessor);
		switch (Objects.requireNonNull(accessor.getCommand())) {
			case SUBSCRIBE -> handleSubscribe(accessor, userId);
			case SEND -> handleSendMessage(accessor);
			default -> {
				log.error("Unsupported command: {}", accessor.getCommand());
				throw new CustomException(ResponseCode.ETC_ERROR);
			}
		}
	}

	@Override
	public boolean supports(StompHeaderAccessor accessor) {
		boolean supports = StompCommand.SUBSCRIBE.equals(accessor.getCommand()) ||
			StompCommand.SEND.equals(accessor.getCommand());
		if (!supports) {
			log.debug("Handler {} support accessor 없음: {}", this.getClass().getSimpleName(), accessor);
		}
		return supports;
	}

	//SUBSCRIBE
	private void handleSubscribe(StompHeaderAccessor accessor, Long userId) {
		try {
			ChatRoomId chatRoomId =new ChatRoomId(extractRoomId(Objects.requireNonNull(accessor.getDestination())));
			Objects.requireNonNull(accessor.getSessionAttributes()).put("chatRoomId", chatRoomId.id());
			handler.handleSubscribe(chatRoomId, new UserId(userId));
			log.info("User {} chat room {}에 성공적으로 구독 완료", userId, chatRoomId.id());
		} catch (Exception e) {
			log.error("SUBSCRIBE 에러: ", e);
			throw new IllegalArgumentException("SUBSCRIBE 실패", e);
		}
	}

	//SEND (DM 일 때만 따로 처리)
	private void handleSendMessage(StompHeaderAccessor accessor) {
		try {
			String chatRoomId = extractRoomId(Objects.requireNonNull(accessor.getDestination()));
			Long userId = (Long)Objects.requireNonNull(accessor.getSessionAttributes()).get("userId");
			// 따로 처리하기
			if(getChatType(chatRoomId).equals("DM")){
				Long friendId = getFriendId(chatRoomId, userId);
				validateDMChatRoomUserCount(chatRoomId,friendId);
			}
		} catch (Exception e) {
			log.error("handleSendMessage Error!!: ", e);
			throw new RuntimeException(e);
		}
	}

	//userId 추출
	private Long extractUserAuth(StompHeaderAccessor accessor) {
		Principal principal = accessor.getUser();
		if (principal == null) {
			throw new CustomException(ResponseCode.AUTH_FAILED);
		}
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) principal;
		BaeMoUserDetails user = (BaeMoUserDetails)auth.getPrincipal();
		return user.userId();
	}

	// 친구Id 추출
	public Long getFriendId(String chatRoomId, Long userId) {
		return ChatRoomUtils.getFriendId(chatRoomId,userId);
	}

	// 채팅 타입 추출
	public String getChatType(String chatRoomId) {
		return ChatRoomUtils.getChatType(chatRoomId);
	}

	private String extractRoomId(String destination) {
		int lastIndex = destination.lastIndexOf('/');
		if (lastIndex == -1) {
			throw new IllegalArgumentException("destination format 형식 유효하지 않음: " + destination);
		}
		return destination.substring(lastIndex + 1);
	}

	private void validateDMChatRoomUserCount(String chatRoomId, Long friendId) {
		if (chatRoomUserOutPort.getsubscribeChatRoomUser(new ChatRoomId(chatRoomId)) != 2) {
			subscribeChatUseCase.subscribeChatRoom(new UserId(friendId), new ChatRoomId(chatRoomId),
				ChatRoomUserStatus.CONNECTED);
		}
	}
}