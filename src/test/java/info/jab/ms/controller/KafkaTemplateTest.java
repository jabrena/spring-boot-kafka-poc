package info.jab.ms.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Disabled
@Testcontainers
@SpringBootTest
public class KafkaTemplateTest {

    @Container
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    public void testKafkaTemplate(){

        kafkaTemplate.send(TOPIC, "test");
    }

    @KafkaListener(topics = TOPIC, groupId = "KAFKA_GROUP_ID")
    public void listen(String message) {
        System.out.println("Received Message in group KAFKA_GROUP_ID: " + message);
    }
}
