package pipeline.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
	private static final String DESTINATION_PREFIX = "/topic";
	private static final String DESTINATION_SUFFIX = "/messages";

	public static final String DESTINATION = DESTINATION_PREFIX + DESTINATION_SUFFIX;

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker(DESTINATION_PREFIX);
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// provide also an end-point without SockJS
		registry.addEndpoint("/gs-guide-websocket");
		registry.addEndpoint("/gs-guide-websocket").withSockJS();
	}
}