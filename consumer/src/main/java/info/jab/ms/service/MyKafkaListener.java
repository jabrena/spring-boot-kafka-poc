package info.jab.ms.service;

import info.jab.ms.config.KafkaCommons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaListener {

    private static final Logger logger = LoggerFactory.getLogger(MyKafkaListener.class);

    @KafkaListener(
            topics = KafkaCommons.COMMON_TOPIC,
            groupId = KafkaCommons.COMMON_GROUP_ID)
    public void listener(String data) {
        logger.info("Listener received: {}", data);
    }
}
