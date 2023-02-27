package info.jab.ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class MyController {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @PostMapping("/messages")
    public MyRequest publish(@RequestBody MyRequest request){
        kafkaTemplate.send("example_topic", request.message());
        return request;
    }
}

