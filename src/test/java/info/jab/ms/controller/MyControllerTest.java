package info.jab.ms.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Java6BDDAssertions.then;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Disabled
@EnableKafka
@SpringBootTest
@EmbeddedKafka(partitions = 1, controlledShutdown = false,
        brokerProperties = {"listeners=PLAINTEXT://localhost:3333", "port=3333"})
@AutoConfigureMockMvc
public class MyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    public void testConfig() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/kafka")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andReturn();

        then(mvcResult.getResponse().getContentAsString()).isEqualTo("Hello World");

        Consumer consumer = runConsumer();
        ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, TOPIC);
        Assertions.assertEquals("test", singleRecord.value());
    }

    @KafkaListener(topics = TOPIC, groupId = "KAFKA_GROUP_ID")
    public void listen(String message) {
        System.out.println("TESTReceived Messasge in group KAFKA_GROUP_ID: " + message);
    }

    public Consumer runConsumer(){
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.consumerProps("consumer", "false", embeddedKafkaBroker));
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        Consumer<String, String> consumer = new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new StringDeserializer()).createConsumer();
        consumer.subscribe(Collections.singleton(TOPIC));
        return consumer;
    }
}