package info.jab.ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@RestController
public class MyController {

	@Autowired
    KafkaTemplate kafkaTemplate;

	@GetMapping("/kafka")
	public @ResponseBody
    String getBook() {

        kafkaTemplate.send(TOPIC, "test");

		return "Hello World";
	}

}
