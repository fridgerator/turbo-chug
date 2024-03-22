package com.fridgerator.DynamicConsumer.generator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fridgerator.DynamicConsumer.model.GameOfThrone;

import net.datafaker.Faker;

@Service
public class ByteArrayGenerator {
    private static Logger logger = LogManager.getLogger(ByteArrayGenerator.class);

    private KafkaTemplate<String, Object> byteArrayKafkaTemplate;

    @Value("${kafka-topics.names.byte-array-topic}")
    private String byteArrayTopic;

    ByteArrayGenerator(KafkaTemplate<String, Object> byteArrayKafkaTemplate) {
        this.byteArrayKafkaTemplate = byteArrayKafkaTemplate;
    }

    @Async
    public void generateByteArray () throws InterruptedException {
        Faker faker = new Faker();

        while (true) {
            Thread.sleep(5000);

            GameOfThrone got = GameOfThrone.builder()
                .charater(faker.gameOfThrones().character())
                .city(faker.gameOfThrones().city())
                .dragon(faker.gameOfThrones().dragon())
                .house(faker.gameOfThrones().house())
                .quote(faker.gameOfThrones().quote())
                .build();
            
            logger.debug("byte array GOT : {}", got);

            try {
                byteArrayKafkaTemplate.send(byteArrayTopic, got.toBytes());
                byteArrayKafkaTemplate.flush();
            } catch (Exception e) {
                logger.error("Error publishing bytearray : {}", e);
            }
        }
    }
}
