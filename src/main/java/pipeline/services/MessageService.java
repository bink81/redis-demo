package pipeline.services;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
		return StreamSupport.stream(messageRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}

	public Message saveMessage(Message message) {
		return messageRepository.save(message);
	}
}
