package pipeline;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pipeline.model.Message;
import pipeline.services.MessageService;

@EnableAutoConfiguration
@SpringBootApplication
public class Application {

	public static final String TOPIC = "messages";

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	// demo data
	@Bean
	CommandLineRunner init(MessageService messageService) {
		messageService.saveMessage(new Message("1"));
		return null;
	}
}