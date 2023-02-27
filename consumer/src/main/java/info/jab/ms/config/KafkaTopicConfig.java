package info.jab.ms.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

//@Configuration
public class KafkaTopicConfig {

    //@Bean
    public NewTopic Code_topic(){
        return TopicBuilder.name("example_topic").build();
    }
}
