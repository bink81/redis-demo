package pipeline.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class MessageNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MessageNotFoundException(Long id) {
		super("could not find message with id'" + id + "'.");
	}
}
