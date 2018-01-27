package pipeline.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pipeline.model.Message;
import pipeline.services.MessageService;

public class Receiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

	@Autowired
	private MessageService messageService;

	public Receiver() {
	}

	public void receiveMessage(String message) {
		LOGGER.info("Received <" + message + ">");
		Message user = new Message(message);
		messageService.saveMessage(user);
	}
}
