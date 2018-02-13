package pipeline.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This class represents user provided data.
 */
@Entity
public class IncomingMessage {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String text;

	public IncomingMessage(String text) {
		this.text = text;
	}

	// for JPA
	protected IncomingMessage() {
	}

	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public void setName(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", text=" + text + "]";
	}
}
