package pipeline.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pipeline.model.Message;
import pipeline.services.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {
	@Autowired
	private MessageService messageService;

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	Message findMessage(@PathVariable Long id) {
		// saveMessage("12");
		return messageService.findMessage(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> addMessage(@RequestBody String text) {
		messageService.saveMessage(new Message(text));
		return ResponseEntity.ok().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	Collection<Message> findMessages() {
		return messageService.findMessages();
	}
}
