package com.fridgerator.TurboChug.kafka;

import org.apache.kafka.common.serialization.Deserializer;

import io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer;

public class TurboKafkaAvroDeserializer extends AbstractKafkaAvroDeserializer implements Deserializer {
    
    @Override
    public void close() {}

    @Override
    public Object deserialize(String s, byte[] data) {
        return (Object) this.deserialize(data);
    }
}
