package pipeline.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import pipeline.model.IncomingMessage;
import pipeline.services.MessageService;
import pipeline.websocket.OutgoingMessage;
import pipeline.websocket.WebSocketConfig;

/**
 * This class receives a message from Redis, persists it and sends to
 * subscribers.
 */
@Controller
public class MessageReceiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private MessageService messageService;

	public MessageReceiver() {
	}

	public void receiveMessage(String message) {
		LOGGER.debug("Received <{}>", message);
		IncomingMessage incomingMessage = new IncomingMessage(message);

		// Send notification(s) only when persistence works
		messageService.saveMessage(incomingMessage);
		template.convertAndSend(WebSocketConfig.DESTINATION, new OutgoingMessage(message));
	}
}
