package pipeline;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import pipeline.model.Message;
import pipeline.redis.MessageReceiver;
import pipeline.services.MessageService;

@EnableAutoConfiguration
@SpringBootApplication
public class Application {

	public static final String TOPIC = "messages";

	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic(TOPIC));

		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(MessageReceiver messageReceiver) {
		return new MessageListenerAdapter(messageReceiver, "receiveMessage");
	}

	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner init(MessageService messageService) {
		// demo data
		messageService.saveMessage(new Message("dummy payload"));
		return null;
	}
}