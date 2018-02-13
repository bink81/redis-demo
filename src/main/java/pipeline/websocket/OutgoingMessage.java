package pipeline.websocket;

/**
 * This class represents the data that is sent out to registered subscribers.
 */
public class OutgoingMessage {

	private String text;

	public OutgoingMessage(String text) {
		this.setText(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}