package info.jab.ms.controller;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(
        partitions = 1,
        controlledShutdown = true,
        brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@TestPropertySource(properties = "spring.kafka.bootstrap-servers=localhost:9092")
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public class MyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @TestConfiguration
    static class config {

        @Bean
        public NewTopic Code_topic(){
            return TopicBuilder.name("example_topic2").build();
        }
    }

    @Test
    public void shouldSendMessageToKafka() throws Exception {

        //Given
        String address = "http://localhost:" + port + "/api/v1/messages";

        //When
        MyRequest payload = new MyRequest("Hello World");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MyRequest> request = new HttpEntity<>(payload, headers);
        ResponseEntity<MyRequest> result = this.restTemplate.postForEntity(address, request, MyRequest.class);

        //Then
        then(result.getBody().message()).isEqualTo("Hello World");
    }
}