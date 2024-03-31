package com.fridgerator.TurboChug.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fridgerator.TurboChug.kafka.KafkaListenerCreator;

@RestController
public class KafkaTestingController {
    KafkaListenerCreator kafkaListenerCreator;

    KafkaTestingController(KafkaListenerCreator kafkaListenerCreator) {
        this.kafkaListenerCreator = kafkaListenerCreator;
    }

    @GetMapping(path = "/create")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestParam String topic) {
        kafkaListenerCreator.createAndRegisterListener(topic);
    }
}
