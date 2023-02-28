package info.jab.ms.service;

import info.jab.ms.config.KafkaCommons;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    private

    @KafkaListener(topics = KafkaCommons.COMMON_TOPIC, groupId = "groupId")
    void listener(String data){
     System.out.println(("Listener received: "+ data + " "));
    }
}
