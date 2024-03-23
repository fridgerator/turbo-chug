package com.fridgerator.DynamicConsumer.generator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import net.datafaker.Faker;

@Service
public class StringGenerator {
    private static Logger logger = LogManager.getLogger(StringGenerator.class);

    private KafkaTemplate<String, String> stringKafakKafkaTemplate;

    @Value("${kafka-topics.names.string-topic}")
    private String stringTopic;

    StringGenerator(KafkaTemplate<String, String> stringKafkaTemplate) {
        this.stringKafakKafkaTemplate = stringKafkaTemplate;
    }

    @Async
    public void generateString () throws InterruptedException {
        Faker faker = new Faker();

        while (true) {
            Thread.sleep(5000);

            String msg = faker.lorem().paragraph();

            logger.debug("string : {}", msg);

            try {
                stringKafakKafkaTemplate.send(stringTopic, msg);
                stringKafakKafkaTemplate.flush();
            } catch (Exception e) {
                logger.error("Error publishing string : {}", e);
            }
        }
    }
}
