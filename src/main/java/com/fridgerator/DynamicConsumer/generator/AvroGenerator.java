package com.fridgerator.DynamicConsumer.generator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fridgerator.avro.transactions.Transaction;

import net.datafaker.Faker;

@Service
public class AvroGenerator {
    private static Logger logger = LogManager.getLogger(AvroGenerator.class);

    private KafkaTemplate<String, Object> avroKafkaTemplate;

    @Value("${kafka-topics.names.avro-topic}")
    private String avroTopic;

    AvroGenerator(KafkaTemplate<String, Object> avroKafkaTemplate) {
        this.avroKafkaTemplate = avroKafkaTemplate;
    }

    @Async
    public void generateAvro () throws InterruptedException {
        Faker faker = new Faker();

        while (true) {
            Thread.sleep(5000);

            Transaction transaction = new Transaction(
                faker.number().randomDouble(2, 1, 100),
                faker.number().randomDouble(2, 1, 100),
                faker.commerce().productName()
            );

            logger.debug("avro transaction : ", transaction);

            try {
                avroKafkaTemplate.send(avroTopic, transaction);
                avroKafkaTemplate.flush();
            } catch (Exception e) {
                logger.error("Error publishing avro : {}", e);
            }
        }
    }
}
