package pipeline.websocket;

public class NotificationMessage {

	private String text;

	public NotificationMessage(String text) {
		this.setText(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}