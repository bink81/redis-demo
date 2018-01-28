package pipeline.rest;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pipeline.Application;
import pipeline.model.Message;
import pipeline.services.MessageService;

@RestController
@RequestMapping("/v1/messages")
public class MessageController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	private MessageService messageService;

	@Autowired
	private ApplicationContext applicationContext;

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	Message findMessage(@PathVariable Long id) {
		return messageService.findMessage(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> addMessage(@RequestBody String text) {
		sendMessageToRedis(text);
		return ResponseEntity.ok().build();
	}

	private void sendMessageToRedis(String text) {
		StringRedisTemplate template = applicationContext.getBean(StringRedisTemplate.class);
		LOGGER.info("Sending message: " + text);
		template.convertAndSend(Application.TOPIC, text);
	}

	@RequestMapping(method = RequestMethod.GET)
	Collection<Message> findMessages() {
		return messageService.findMessages();
	}
}
