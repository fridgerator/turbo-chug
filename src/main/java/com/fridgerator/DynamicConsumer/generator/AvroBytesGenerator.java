package com.fridgerator.DynamicConsumer.generator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fridgerator.avro.transactions.Futurama;

import net.datafaker.Faker;

@Service
public class AvroBytesGenerator {
    private static Logger logger = LogManager.getLogger(AvroBytesGenerator.class);

    private KafkaTemplate<String, Object> avroBytesKafkaTemplate;

    @Value("${kafka-topics.names.avro-bytes-topic}")
    private String avroBytesTopic;

    AvroBytesGenerator(KafkaTemplate<String, Object> avroBytesKafkaTemplate) {
        this.avroBytesKafkaTemplate = avroBytesKafkaTemplate;
    }

    @Async
    public void generateAvroBytes () throws InterruptedException {
        Faker faker = new Faker();

        while (true) {
            Thread.sleep(5000);

            Futurama futurama = new Futurama(faker.futurama().character(), faker.futurama().hermesCatchPhrase(),
                    faker.futurama().location(), faker.futurama().quote(), faker.number().randomDouble(2, 1, 100));

            logger.info("avro bytes futurama : {}", futurama);

            try {
                avroBytesKafkaTemplate.send(avroBytesTopic, futurama);
                avroBytesKafkaTemplate.flush();
            } catch (Exception e) {
                logger.error("Error publishing avro bytes : {}", e);
            }
        }
    }
}
