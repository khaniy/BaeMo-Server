package hotil.baemo.config.socket.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class StompErrorHandler extends StompSubProtocolErrorHandler {
    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        log.error(ex.getMessage(), ex);
        if (ex instanceof MessageDeliveryException) {
            byte[] messageBody = ex.getMessage().getBytes(StandardCharsets.UTF_8);
            StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
            accessor.setLeaveMutable(true);

            return MessageBuilder.createMessage(messageBody, accessor.getMessageHeaders());

        }
        return super.handleClientMessageProcessingError(clientMessage, ex);
    }
}
