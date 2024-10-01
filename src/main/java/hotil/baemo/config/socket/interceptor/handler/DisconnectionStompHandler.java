package hotil.baemo.config.socket.interceptor.handler;

import java.security.Principal;
import java.util.Objects;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.chat.adapter.output.repository.memory.ChatRedisRepository;
import hotil.baemo.domains.chat.application.ports.output.port.CommandChatRoomUserOutPort;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class DisconnectionStompHandler implements StompHandler {
    private final ChatRedisRepository chatRedisRepository;
	private final CommandChatRoomUserOutPort commandChatRoomUserOutPort;


    @Override
    public void handleMessage(StompHeaderAccessor accessor) {
		log.info("DisconnectionStompHandler.handleMessage");
		String chatRoomId = (String)Objects.requireNonNull(accessor.getSessionAttributes()).get("chatRoomId");
		if (chatRoomId != null) {
			try {
				Long userId = extractUserAuth(accessor);
				disconnectUserFromChatRoom(chatRoomId, userId);
			} catch (Exception e) {
				log.error("DISCONNECT: {}", e.getMessage(), e);
				throw new RuntimeException(e);
			}
		}
	}

    @Override
    public boolean supports(StompHeaderAccessor accessor) {
        return StompCommand.DISCONNECT.equals(accessor.getCommand());
    }

    //DISCONNECT시 상태 변경
    private void disconnectUserFromChatRoom(String chatRoomId, Long userId) {
		commandChatRoomUserOutPort.updateChatRoomStatus(new ChatRoomId(chatRoomId), new UserId(userId), ChatRoomUserStatus.DISCONNECTED);
		chatRedisRepository.removeUserFromChatRoom(chatRoomId, userId);
	}

	private Long extractUserAuth(StompHeaderAccessor accessor) {
		Principal principal = accessor.getUser();
		if (principal == null) {
			throw new CustomException(ResponseCode.AUTH_FAILED);
		}
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) principal;
		BaeMoUserDetails user = (BaeMoUserDetails) auth.getPrincipal();
		return user.userId();
	}

}