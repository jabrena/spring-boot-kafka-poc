package info.jab.ms.service;

import info.jab.ms.config.KafkaCommons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyServiceImpl implements MyService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public void send(String payload) {
        kafkaTemplate.send(KafkaCommons.COMMON_TOPIC, payload);
    }
}
