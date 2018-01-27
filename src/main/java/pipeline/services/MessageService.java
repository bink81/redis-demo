package pipeline.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pipeline.dao.MessageRepository;
import pipeline.model.Message;

@Service
@Transactional
public class MessageService {
	@Autowired
	private MessageRepository messageRepository;

	public Message findMessage(Long id) {
		Message message = messageRepository.findOne(id);
		if (message == null) {
			throw new MessageNotFoundException(id);
		}
		return message;
	}

	public Collection<Message> findMessages() {
		return messageRepository.findAll();
	}

	public Message saveMessage(Message message) {
		return messageRepository.save(message);
	}
}
