package hotil.baemo.config.socket.interceptor.handler;

import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

public interface StompHandler {
    void handleMessage(StompHeaderAccessor accessor);
    boolean supports(StompHeaderAccessor accessor);
}
