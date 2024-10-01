package hotil.baemo.config.socket.interceptor.handler;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.util.Set;

public abstract class MessagingStompHandler implements StompHandler {

    @Override
    public boolean supports(StompHeaderAccessor accessor) {
        return checkStompCommand(accessor);
    }

    private boolean checkStompCommand(StompHeaderAccessor accessor) {
        Set<StompCommand> validCommands = Set.of(
                StompCommand.SUBSCRIBE,
                StompCommand.UNSUBSCRIBE,
                StompCommand.MESSAGE,
                StompCommand.SEND);
        return validCommands.contains(accessor.getCommand());
    }
}

