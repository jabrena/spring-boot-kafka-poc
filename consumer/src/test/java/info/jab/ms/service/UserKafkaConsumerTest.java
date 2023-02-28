package info.jab.ms.service;

import info.jab.ms.config.KafkaCommons;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@EmbeddedKafka(
        partitions = 1,
        controlledShutdown = true,
        brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" },
        topics = { KafkaCommons.COMMON_TOPIC })
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=localhost:9092",
        "spring.kafka.consumer.auto-offset-reset=earliest"
})
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserKafkaConsumerTest {

    @SpyBean
    private MyKafkaListener userKafkaConsumer;

    @Captor
    ArgumentCaptor<String> messageCaptor;

    private Producer<String, String> producer;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @BeforeAll
    void setUp() {
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        producer = new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new StringSerializer()).createProducer();
    }

    @Test
    void testLogKafkaMessages() {

        //Given
        String expectedMessage = "Hello World";
        producer.send(new ProducerRecord<>(KafkaCommons.COMMON_TOPIC, expectedMessage));
        producer.flush();

        //When
        verify(userKafkaConsumer, timeout(5000).times(1))
                .listener(messageCaptor.capture());
        String result = messageCaptor.getValue();

        //Then
        then(result).isEqualTo(expectedMessage);
    }

    @AfterAll
    void shutdown() {
        producer.close();
    }
}