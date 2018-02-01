package pipeline.services;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pipeline.dao.MessageRepository;
import pipeline.model.IncomingMessage;

@Service
@Transactional
public class MessageService {
	@Autowired
	private MessageRepository messageRepository;

	public IncomingMessage findMessage(Long id) {
		IncomingMessage message = messageRepository.findOne(id);
		if (message == null) {
			throw new MessageNotFoundException(id);
		}
		return message;
	}

	public Collection<IncomingMessage> findMessages() {
		return StreamSupport.stream(messageRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	public IncomingMessage saveMessage(IncomingMessage message) {
		IncomingMessage saved = messageRepository.save(message);
		return saved;
	}
}
