package info.jab.ms.service;

import info.jab.ms.config.KafkaCommons;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
@EmbeddedKafka(
        partitions = 1,
        controlledShutdown = true,
        brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" },
        topics = { KafkaCommons.COMMON_TOPIC })
@TestPropertySource(properties = "spring.kafka.bootstrap-servers=localhost:9092")
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public class MyServiceTest {

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private MyService myService;

    @Test
    public void should_read_message_sent() {

        //Given
        Consumer<String, String> consumer = configureConsumer();
        String expectedMessage = "Hello World";

        //When
        myService.send(expectedMessage);

        //Then
        ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, KafkaCommons.COMMON_TOPIC);
        then(singleRecord.value()).isEqualTo(expectedMessage);

        consumer.close();
    }

    private Consumer<String, String> configureConsumer() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        Consumer<String, String> consumer =
                new DefaultKafkaConsumerFactory<String, String>(consumerProps).createConsumer();
        consumer.subscribe(Collections.singleton(KafkaCommons.COMMON_TOPIC));
        return consumer;
    }
}