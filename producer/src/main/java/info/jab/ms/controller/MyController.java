package info.jab.ms.controller;

import info.jab.ms.service.MyService;
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
    private MyService myService;

    @PostMapping("/messages")
    public MyRequest publish(@RequestBody MyRequest request){
        myService.send(request.message());
        return request;
    }
}

