package hotil.baemo.config.socket.interceptor;

import hotil.baemo.config.socket.interceptor.handler.StompHandler;
import hotil.baemo.config.socket.interceptor.provider.StompHandlerProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class StompInterceptor implements ChannelInterceptor {

    private final StompHandlerProvider stompHandlerProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        StompHandler stompHandler = stompHandlerProvider.getHandler(accessor);
        stompHandler.handleMessage(accessor);
        return message;
    }
}