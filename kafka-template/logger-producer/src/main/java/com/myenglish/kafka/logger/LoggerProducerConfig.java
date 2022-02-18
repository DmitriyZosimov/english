package com.myenglish.kafka.logger;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = {"com.myenglish.kafka.logger"})
@PropertySource("classpath:logger-producer.properties")
public class LoggerProducerConfig {

    @Value("${bootstrap.servers}")
    private String bootstrapServers;

    @Value("${buffer.memory}")
    private String bufferMemory;

    @Value("${linger.ms}")
    private String lingerMs;

    @Value("${batch.size}")
    private String batchSize;

    @Value("${delivery.timeout.ms}")
    private String deliveryTimeoutMs;

    @Value("${max.block.ms}")
    private String msxBlockMs;

    @Value("${acks}")
    private String acks;

    @Value("${enable.idempotence}")
    private String enableIdempotence;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(getProperties());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        kafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
        return kafkaTemplate;
    }

    private Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.ACKS_CONFIG, acks);
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        properties.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeoutMs);
        properties.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return properties;
    }

}
