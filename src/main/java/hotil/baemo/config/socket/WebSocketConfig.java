package hotil.baemo.config.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import hotil.baemo.config.socket.interceptor.StompInterceptor;
import hotil.baemo.config.socket.transport.StompSessionTransport;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private final StompInterceptor stompInterceptor;
	private final StompSessionTransport stompSessionTransport;


	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(WebSocketProperties.CHAT_CONNECTION_URL)
			.setAllowedOrigins("*");
//		registry.addEndpoint(WebSocketProperties.SCOREBOARD_CONNECTION_URL)
//			.setAllowedOriginPatterns("*");
	}

	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
		registration.addDecoratorFactory(stompSessionTransport);
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes(WebSocketProperties.CHAT_EVENT_PREFIX_URL);
//			WebSocketProperties.SCOREBOARD_EVENT_PREFIX_URL);
		registry.enableSimpleBroker(WebSocketProperties.CHAT_SUBSCRIBE_URL);
//			WebSocketProperties.SCOREBOARD_SUBSCRIBE_URL);
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(stompInterceptor);
	}

}
