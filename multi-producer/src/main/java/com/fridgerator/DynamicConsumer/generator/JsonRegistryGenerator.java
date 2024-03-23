package com.fridgerator.DynamicConsumer.generator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fridgerator.DynamicConsumer.model.Retailer;

import net.datafaker.Faker;

@Service
public class JsonRegistryGenerator {
    private static Logger logger = LogManager.getLogger(JsonRegistryGenerator.class);

    private KafkaTemplate<String, Object> jsonRegistryKafkaTemplate;

    @Value("${kafka-topics.names.json-registry-topic}")
    private String jsonRegistryTopic;

    JsonRegistryGenerator(@Qualifier("jsonRegistryKafkaTemplate") KafkaTemplate<String, Object> jsonRegistryKafkaTemplate) {
        this.jsonRegistryKafkaTemplate = jsonRegistryKafkaTemplate;
    }

    @Async
    public void generateRegistryJson() throws InterruptedException {
        Faker faker = new Faker();

        while (true) {
            Thread.sleep(5000);

            Retailer retailer = new Retailer(faker.company().name(), faker.address().streetAddress());

            logger.debug("json registry retailer : {}", retailer);

            try {
                jsonRegistryKafkaTemplate.send(jsonRegistryTopic, retailer);
                jsonRegistryKafkaTemplate.flush();
                logger.debug("json registry sent");
            } catch (Exception e) {
                logger.error("Error publishing registry json : {}", e);
            }
        }
    }
}
