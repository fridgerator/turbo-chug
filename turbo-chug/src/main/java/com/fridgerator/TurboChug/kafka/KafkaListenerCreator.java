package com.fridgerator.TurboChug.kafka;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Consumer;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Service;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;

@Service
public class KafkaListenerCreator {
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private KafkaListenerContainerFactory kafkaListenerContainerFactory;

    String kafkaGroupId = "KafkaGroupID";
    String kafkaListenerId = "KafkaListenerID-";
    static AtomicLong endpointIdIndex = new AtomicLong(1);

    KafkaListenerCreator(KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry, KafkaListenerContainerFactory kafkaListenerContainerFactory) {
        this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
        this.kafkaListenerContainerFactory = kafkaListenerContainerFactory;
    }

    public void createAndRegisterListener(String topic) {
        var listener = createKafkaListenerEndpoint(topic);
        kafkaListenerEndpointRegistry.registerListenerContainer(listener, kafkaListenerContainerFactory, true);
    }

    private KafkaListenerEndpoint createKafkaListenerEndpoint(String topic) {
        var kafkaListenerEndpoint = createMethodKafkaListenerEndpoint(topic);
        kafkaListenerEndpoint.setBean(new KafkaTemplateListener());

        try {
            kafkaListenerEndpoint.setMethod(KafkaTemplateListener.class.getMethod("onMessage", ConsumerRecord.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Attempt to call a non-existent method " + e);
        }

        return kafkaListenerEndpoint;
    }

    private MethodKafkaListenerEndpoint<String, Object> createMethodKafkaListenerEndpoint(String topic) {
        MethodKafkaListenerEndpoint<String, Object> kafkaListenerEndpoint = new MethodKafkaListenerEndpoint<>();
        kafkaListenerEndpoint.setId(generateListenerId());
        kafkaListenerEndpoint.setGroupId(kafkaGroupId);
        kafkaListenerEndpoint.setAutoStartup(true);
        kafkaListenerEndpoint.setTopics(topic);
        Properties properties = new Properties();
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        // properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        kafkaListenerEndpoint.setConsumerProperties(properties);
        kafkaListenerEndpoint.setMessageHandlerMethodFactory(new DefaultMessageHandlerMethodFactory());
        return kafkaListenerEndpoint;
    }

    private String generateListenerId() {
        return kafkaListenerId + endpointIdIndex.getAndIncrement();

    }
}
