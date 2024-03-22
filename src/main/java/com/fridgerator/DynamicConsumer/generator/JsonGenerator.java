package com.fridgerator.DynamicConsumer.generator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fridgerator.DynamicConsumer.model.Customer;

import net.datafaker.Faker;

@Service
public class JsonGenerator {
    private static Logger logger = LogManager.getLogger(JsonGenerator.class);

    private KafkaTemplate<String, Object> jsonKafkaTemplate;

    @Value("${kafka-topics.names.customers-json}")
    private String customersTopic;

    JsonGenerator(KafkaTemplate<String, Object> jsonKafkaTemplate) {
        this.jsonKafkaTemplate = jsonKafkaTemplate;
    }

    @Async
    public void generateJson () throws InterruptedException {
        Faker faker = new Faker();

        while (true) {
            Thread.sleep(5000);

            Customer customer = new Customer(faker.name().fullName(), faker.address().streetAddress());
            
            logger.debug("json customer : {}", customer);

            try {
                jsonKafkaTemplate.send(customersTopic, customer);
                jsonKafkaTemplate.flush();
            } catch (Exception e) {
                logger.error("Error publishing json : {}", e);
            }
        }   
    }
}
