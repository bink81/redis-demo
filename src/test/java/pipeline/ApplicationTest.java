package pipeline;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import pipeline.dao.MessageRepository;
import pipeline.model.IncomingMessage;

/*
 * This test suite requires an externally running Redis and database services
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ApplicationTest {

	private static final String DUMMY_CONTENT = "dummy_content";

	private static final String MESSAGES_PATH = "/v1/messages/";

	private static final String MISSING_MESSAGE_ID = "1111";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private IncomingMessage message;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private MessageRepository messageRepository;

	@Before
	public void setup() throws Exception {
		mockMvc = webAppContextSetup(webApplicationContext).build();
		messageRepository.deleteAll();

		// persist the message to test GET methods
		message = messageRepository.save(new IncomingMessage("dummy text"));
	}

	@Test
	public void givenAlreadyCreatedMessage_whenQueryForItsId_thenItIsFound() throws Exception {
		mockMvc.perform(get(MESSAGES_PATH + message.getId()).contentType(contentType)).andExpect(status().isOk())
				.andExpect(content().contentType(contentType));
	}

	@Test
	public void givenAlreadyCreatedMessage_whenQueryForAll_thenOneIsFound() throws Exception {
		verifyThatNumberOfPersistedMessagesEquals(1);
	}

	@Test
	public void givenAlreadyCreatedMessage_whenQueryForAnotherId_thenNoneIsFound() throws Exception {
		mockMvc.perform(get(MESSAGES_PATH + MISSING_MESSAGE_ID).contentType(contentType))
				.andExpect(status().isNotFound());
	}

	@Test
	public void givenAlreadyCreatedMessage_whenRequestingPost_thenSuccess() throws Exception {
		mockMvc.perform(post(MESSAGES_PATH).content(DUMMY_CONTENT).contentType(contentType)).andExpect(status().isOk());
	}

	@Test
	public void givenAlreadyCreatedMessage_whenRequestingPostWithoutBody_thenFail() throws Exception {
		mockMvc.perform(post(MESSAGES_PATH).contentType(contentType)).andExpect(status().isBadRequest());
	}

	@Test
	public void givenAlreadyCreatedMessage_whenRequestingPost_thenWeGetAnotherRecord() throws Exception {
		mockMvc.perform(post(MESSAGES_PATH).content(DUMMY_CONTENT).contentType(contentType)).andExpect(status().isOk());
		// We need to wait a bit until this asynchronuous operation (via Redis listener)
		// finishes
		Thread.sleep(100);

		verifyThatNumberOfPersistedMessagesEquals(2);
	}

	private void verifyThatNumberOfPersistedMessagesEquals(int i) throws Exception {
		mockMvc.perform(get(MESSAGES_PATH).contentType(contentType)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(i)));
	}
}
