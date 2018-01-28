package pipeline.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import pipeline.model.Message;
import pipeline.services.MessageService;
import pipeline.websocket.NotificationMessage;
import pipeline.websocket.WebSocketConfig;

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
		LOGGER.info("Received <" + message + ">");
		Message user = new Message(message);
		messageService.saveMessage(user);
		this.template.convertAndSend(WebSocketConfig.DESTINATION, new NotificationMessage(message));
	}
}
