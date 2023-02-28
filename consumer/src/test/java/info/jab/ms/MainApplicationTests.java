package info.jab.ms;

import info.jab.ms.config.KafkaCommons;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@EmbeddedKafka(
		partitions = 1,
		controlledShutdown = true,
		brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" },
		topics = { KafkaCommons.COMMON_TOPIC })
@TestPropertySource(properties = "spring.kafka.bootstrap-servers=localhost:9092")
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public class MainApplicationTests {

	@Test
	public void contextLoads() {
	}
}
