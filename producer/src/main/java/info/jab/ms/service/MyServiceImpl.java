package info.jab.ms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyServiceImpl implements MyService {

    private static final String TOPIC = "example_topic2";

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public void send(String payload) {
        kafkaTemplate.send(TOPIC, payload);
    }
}
