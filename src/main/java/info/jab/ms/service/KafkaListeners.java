package info.jab.ms.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics="example_topic", groupId = "groupId")
    void listener(String data){
     System.out.println(("Listener received: "+ data + " "));
    }
}
