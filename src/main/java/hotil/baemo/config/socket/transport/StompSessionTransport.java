package hotil.baemo.config.socket.transport;

import hotil.baemo.config.socket.manager.StompLocalSessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

@RequiredArgsConstructor
@Component
public class StompSessionTransport implements WebSocketHandlerDecoratorFactory {

	private final StompLocalSessionManager sessionManager;

	@Override
	public WebSocketHandler decorate(WebSocketHandler handler) {
		return new WebSocketHandlerDecorator(handler) {
			@Override
			public void afterConnectionEstablished(WebSocketSession session) throws Exception {
				super.afterConnectionEstablished(session);
				sessionManager.addSession(session);
			}
			@Override
			public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
				super.afterConnectionClosed(session, closeStatus);
				sessionManager.popSession(session);
			}
		};
	}
}