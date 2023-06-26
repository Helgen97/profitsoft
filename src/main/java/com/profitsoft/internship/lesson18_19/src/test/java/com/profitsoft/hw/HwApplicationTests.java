package com.profitsoft.hw;

import com.profitsoft.hw.config.ElasticTestConfig;
import com.profitsoft.hw.data.MessageData;
import com.profitsoft.hw.data.MessageStatus;
import com.profitsoft.hw.messaging.ReceivedMessage;
import com.profitsoft.hw.services.implementations.MessageService;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.stream.Collectors;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {HwApplication.class, ElasticTestConfig.class})
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class HwApplicationTests {

	@Value("${kafka.topic.service}")
	private String kafkaTopic;

	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	@Autowired
	private KafkaOperations<String, ReceivedMessage> kafkaOperations;

	@SpyBean
	private MessageService messageService;

	@MockBean
	private JavaMailSender mailSender;

	private final String correctEmail = "test@mail.com";
	private final String incorrectEmail = "test";

	@BeforeEach
	void setUp() {
		if(!elasticsearchOperations.indexOps(MessageData.class).exists()) {
			elasticsearchOperations.indexOps(MessageData.class).createMapping();
		}
	}

	@Test
	void whenSendMessageToKafkaWithCorrectMail_thenReceivedMessageSaveToDBAndSendToRecipients() {
		doNothing().when(mailSender).send((MimeMessage) any());
		doReturn(new MimeMessage((Session) null)).when(mailSender).createMimeMessage();

		ReceivedMessage receivedMessage = buildMessage(correctEmail, 3L);
		kafkaOperations.send(kafkaTopic, receivedMessage.getSubject(), receivedMessage);

		verify(messageService, after(5000)).saveMessage(any());
		verify(mailSender, times(1)).createMimeMessage();
		verify(mailSender, times(1)).send((MimeMessage) any());

		List<MessageData> succeededMessages = getAllMessagesByStatus(MessageStatus.SUCCESS);
		assertThat(succeededMessages.size()).isEqualTo(1);
		assertThat(succeededMessages.get(0).getSubject()).isEqualTo("Test subject");
		assertThat(succeededMessages.get(0).getContent()).isEqualTo("Test content");
		assertThat(succeededMessages.get(0).getRecipients()).isEqualTo(List.of(correctEmail));
	}

	@Test
	void whenSendMessageToKafkaWithIncorrectMail_thenReceivedMessageSaveToDBAndThrowAddressException() {
		doThrow(MailSendException.class).when(mailSender).send((MimeMessage) any());
		doReturn(new MimeMessage((Session) null)).when(mailSender).createMimeMessage();

		ReceivedMessage receivedMessage = buildMessage(incorrectEmail, 2L);
		kafkaOperations.send(kafkaTopic, receivedMessage.getSubject(), receivedMessage);

		verify(messageService, after(5000)).saveMessage(any());
		verify(mailSender, times(1)).createMimeMessage();
		verify(mailSender, times(1)).send((MimeMessage) any());

		List<MessageData> errorMessages = getAllMessagesByStatus(MessageStatus.ERROR);
		assertThat(errorMessages.size()).isEqualTo(1);
		assertThat(errorMessages.get(0).getSubject()).isEqualTo("Test subject");
		assertThat(errorMessages.get(0).getContent()).isEqualTo("Test content");
		assertThat(errorMessages.get(0).getRecipients()).isEqualTo(List.of(incorrectEmail));
		assertThat(errorMessages.get(0).getErrorMessage()).contains("org.springframework.mail.MailSendException");
	}

	private ReceivedMessage buildMessage(String recipientEmail, long mailingId) {
		return ReceivedMessage.builder()
				.mailingId(mailingId)
				.subject("Test subject")
				.content("Test content")
				.recipients(List.of(recipientEmail))
				.build();
	}

	private List<MessageData> getAllMessagesByStatus(MessageStatus status) {
		Criteria criteria = new Criteria(MessageData.Fields.messageStatus).is(status);
		CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
		return elasticsearchOperations.search(criteriaQuery, MessageData.class).stream()
				.map(SearchHit::getContent)
				.collect(Collectors.toList());
	}

}
