package com.fridgerator.DynamicConsumer.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fridgerator.DynamicConsumer.generator.AvroGenerator;
import com.fridgerator.DynamicConsumer.generator.JsonGenerator;
import com.fridgerator.DynamicConsumer.generator.JsonRegistryGenerator;
import com.fridgerator.DynamicConsumer.generator.StringGenerator;

import jakarta.annotation.PostConstruct;

@Component
public class PostConstructCaller {
    private static final Logger logger = LogManager.getLogger(PostConstructCaller.class);

    private JsonGenerator jsonGenerator;
    private StringGenerator stringGenerator;
    private JsonRegistryGenerator jsonRegistryGenerator;
    private AvroGenerator avroGenerator;

    PostConstructCaller(JsonGenerator jsonGenerator, StringGenerator stringGenerator, JsonRegistryGenerator jsonRegistryGenerator, AvroGenerator avroGenerator) {
        this.jsonGenerator = jsonGenerator;
        this.stringGenerator = stringGenerator;
        this.jsonRegistryGenerator = jsonRegistryGenerator;
        this.avroGenerator = avroGenerator;
    }

    @PostConstruct
    public void initJson() throws InterruptedException {
        logger.info("starting json generator");
        jsonGenerator.generateJson();
    }

    @PostConstruct
    public void initString() throws InterruptedException {
        logger.info("starting string generator");
        stringGenerator.generateString();
    }

    @PostConstruct
    public void initRegistryJson() throws InterruptedException {
        logger.info("starting json registry generator");
        jsonRegistryGenerator.generateRegistryJson();
    }

    @PostConstruct
    public void initAvro() throws InterruptedException {
        logger.info("starting avro generator");
        avroGenerator.generateAvro();
    }
}
