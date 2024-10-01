package hotil.baemo.config.socket.interceptor.handler;

import static hotil.baemo.config.socket.WebSocketProperties.*;

import java.util.List;

import hotil.baemo.config.socket.error.HandleCustomException;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetailsService;
import hotil.baemo.core.security.jwt.util.JwtUtil;
import hotil.baemo.domains.chat.application.ports.output.postgres.CommandChatRoomUserPostgresAdapter;
import hotil.baemo.domains.chat.adapter.output.postgres.entity.ChatRoomUserEntity;
import hotil.baemo.domains.chat.adapter.output.repository.ChatRoomUserJpaRepository;
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
public class ConnectionStompHandler implements StompHandler {

    private final JwtUtil jwtUtil;
    private final CommandChatRoomUserPostgresAdapter chatRoomUserPostgresAdapter;
    private final ChatRoomUserJpaRepository chatRoomUserJpaRepository;
    private final BaeMoUserDetailsService baeMoUserDetailsService;

    @Override
    @HandleCustomException
    public void handleMessage(StompHeaderAccessor accessor) {
        log.info("ConnectionStompHandler.handleMessage");

        String token = accessor.getFirstNativeHeader("Authorization");

        if (token!=null && jwtUtil.isValidToken(token)) {
            final var userIdx = jwtUtil.getUserIdx(token);
            final var baeMoUsersEntity = baeMoUserDetailsService.loadUserByUserId(userIdx);
            final var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(baeMoUsersEntity,
                null, baeMoUsersEntity.getAuthorities());
            accessor.setUser(usernamePasswordAuthenticationToken);

            String destination = accessor.getDestination();
            if (destination != null && destination.startsWith(CHAT_CONNECTION_URL)) {
                List<ChatRoomUserEntity> chatRoomUsers = chatRoomUserJpaRepository.findByUserId(userIdx);
                for (ChatRoomUserEntity chatRoomUser : chatRoomUsers) {
                    chatRoomUserPostgresAdapter.updateChatRoomStatus(new ChatRoomId(chatRoomUser.getChatRoomId()),
                        new UserId(chatRoomUser.getUserId()), ChatRoomUserStatus.CONNECTED);
                }
            }
        }
    }

    @Override
    public boolean supports(StompHeaderAccessor accessor) {
        return StompCommand.CONNECT.equals(accessor.getCommand());
    }
}