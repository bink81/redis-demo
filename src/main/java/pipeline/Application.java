package pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import pipeline.redis.MessageReceiver;
import pipeline.services.MessageService;

@EnableAutoConfiguration
@SpringBootApplication
public class Application {
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static final String TOPIC = "messages";

	@Bean
	RedisMessageListenerContainer setupRedisContainer(MessageListenerAdapter listenerAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		RedisConnectionFactory connectionFactory = setupRedisConnectionFactory();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic(TOPIC));
		return container;
	}

	@Bean
	MessageListenerAdapter setupRedisListenerAdapter(MessageReceiver messageReceiver) {
		return new MessageListenerAdapter(messageReceiver, "receiveMessage");
	}

	@Bean
	StringRedisTemplate setupRedisTemplate(RedisConnectionFactory connectionFactory) {
		if (null == connectionFactory) {
			LOGGER.error("Redis Template Service not available");
			return null;
		}
		return new StringRedisTemplate(connectionFactory);
	}

	@Bean
	JedisConnectionFactory setupRedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		String redisConnectionUrl = System.getenv("REDIS_PORT");
		LOGGER.info("redisConnectionUrl: {}", redisConnectionUrl);
		if (redisConnectionUrl != null) {
			extractAndAssignRedicConnection(factory, redisConnectionUrl);
		}
		factory.setUsePool(true);
		return factory;
	}

	private void extractAndAssignRedicConnection(JedisConnectionFactory factory, String redisConnectionUrl) {
		String hostURL = redisConnectionUrl.substring("tcp://".length());
		int portSeparatorPosition = hostURL.indexOf(':');

		String hostName = hostURL.substring(0, portSeparatorPosition);
		LOGGER.info("hostName: {}", hostName);
		factory.setHostName(hostName);

		int port = Integer.parseInt(hostURL.substring(portSeparatorPosition + 1));
		factory.setPort(port);
		LOGGER.info("port: {}", port);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner init(MessageService messageService) {
		return null;
	}
}