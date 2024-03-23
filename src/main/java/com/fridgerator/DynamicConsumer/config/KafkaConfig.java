package com.fridgerator.DynamicConsumer.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.fridgerator.DynamicConsumer.util.AvroBytesSerializer;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka-topics.names.json-topic}")
    private String jsonTopic;

    @Value("${kafka-topics.names.string-topic}")
    private String stringTopic;

    @Value("${kafka-topics.names.json-registry-topic}")
    private String jsonRegistryTopic;

    @Value("${kafka-topics.names.avro-topic}")
    private String avroTopic;

    @Value("${kafka-topics.names.byte-array-topic}")
    private String byteArrayTopic;

    @Value("${kafka-topics.names.avro-bytes-topic}")
    private String avroBytesTopic;

    @Value("${kafka-topics.replica-count}")
    int replicaCount;

    @Value("${kafka-topics.partition-count}")
    int partitionCount;

    @Value("${spring.kafka.properties.schema.registry.url}")
    private String schemaRegistryUrl;

    @Bean
    public NewTopics topics() {
        return new NewTopics(
            TopicBuilder.name(jsonTopic)
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build(),
            TopicBuilder.name(stringTopic)
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build(),
            TopicBuilder.name(jsonRegistryTopic)
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build(),
            TopicBuilder.name(avroTopic)
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build(),
            TopicBuilder.name(byteArrayTopic)
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build(),
            TopicBuilder.name(avroBytesTopic)
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build()
        );
    }


    /**
     * AvroBytes producer config
     */
    @Bean
    public ProducerFactory<String, Object> avroBytesProducerFactory() {
        Map<String, Object> configProps = Map.of(
            ProducerConfig.ACKS_CONFIG, "all",
            ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true,
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroBytesSerializer.class
        );

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean(name = "avroBytesKafkaTemplate")
    public KafkaTemplate<String, Object> avroBytesKafkaTemplate() {
        return new KafkaTemplate<>(avroBytesProducerFactory());
    }

    /**
     * ByteArray producer config
     */
    @Bean
    public ProducerFactory<String, Object> byteArrayProducerFactory() {
        Map<String, Object> configProps = Map.of(
            ProducerConfig.ACKS_CONFIG, "all",
            ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true,
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class
        );

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean(name = "byteArrayKafkaTemplate")
    public KafkaTemplate<String, Object> byteArrayKafkaTemplate() {
        return new KafkaTemplate<>(byteArrayProducerFactory());
    }

    /**
     * String producer config
     */
    @Bean
    public ProducerFactory<String, String> stringProducerFactory() {
        Map<String, Object> configProps = Map.of(
            ProducerConfig.ACKS_CONFIG, "all",
            ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true,
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
        );

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean(name = "stringKafkaTemplate")
    public KafkaTemplate<String, String> stringKafkaTemplate() {
        return new KafkaTemplate<>(stringProducerFactory());
    }

    /**
     * Json producer (no schema registry)
     */
    @Bean
    public ProducerFactory<String, Object> jsonProducerFactory() {
        Map<String, Object> configProps = Map.of(
            ProducerConfig.ACKS_CONFIG, "all",
            ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true,
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class
        );

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean(name = "jsonKafkaTemplate")
    public KafkaTemplate<String, Object> jsonKafkaTemplate() {
        return new KafkaTemplate<>(jsonProducerFactory());
    }

    /**
     * Json Schema Registry producer
     */
    @Bean
    public ProducerFactory<String, Object> jsonRegistryProducerFactory() {
        Properties props = new Properties();
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSchemaSerializer.class);
        props.put("schema.registry.url", schemaRegistryUrl);
        Map<String, Object> configProps = new HashMap<>((Map) props);

        // Map<String, Object> configProps = Map.of(
        //     ProducerConfig.ACKS_CONFIG, "all",
        //     ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true,
        //     ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
        //     ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
        //     ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSchemaSerializer.class
        // );
        // configProps.put("schema.registry.url", schemaRegistryUrl); // throws weird null exception

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean(name = "jsonRegistryKafkaTemplate")
    public KafkaTemplate<String, Object> jsonRegistryKafkaTemplate() {
        return new KafkaTemplate<>(jsonRegistryProducerFactory());
    }

    /**
     * Avro producer
     */
    @Bean
    public ProducerFactory<String, Object> avroProducerFactory() {
        Map<String, Object> configProps = Map.of(
            ProducerConfig.ACKS_CONFIG, "all",
            ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true,
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class,
            KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl
        );

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean(name = "avroKafkaTemplate")
    public KafkaTemplate<String, Object> avroKafkaTemplate() {
        return new KafkaTemplate<>(avroProducerFactory());
    }
}
