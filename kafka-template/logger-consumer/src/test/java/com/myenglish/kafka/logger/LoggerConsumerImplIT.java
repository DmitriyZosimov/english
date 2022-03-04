package com.myenglish.kafka.logger;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("kafka")
@ContextConfiguration(classes = {LoggerConsumerConfig.class, LoggerProducerTestConfig.class})
@EmbeddedKafka(partitions = 3, topics = KafkaTopics.LOGGER, value = 3, ports = {9007, 9008, 9009})
@TestPropertySource(properties = {"auto.offset.reset=earliest", "max.poll.interval.ms=2500",
        "bootstrap.servers=localhost:9007,localhost:9008,localhost:9009"})
public class LoggerConsumerImplIT {

    static int QUANTITY = 50;
    @Autowired
    LoggerConsumer consumer;
    @Autowired
    LoggerProducerTest producer;
    CountDownLatch countDownLatch;

    @BeforeEach
    public void setup() {
        countDownLatch = new CountDownLatch(QUANTITY);
        consumer.setCountDownLatch(countDownLatch);
    }

    @Test
    public void testGetMessageFromFirstClient() {
        producer.sendLog(0, 1);
        sleep();
        Assertions.assertEquals(0L, countDownLatch.getCount());
    }

    @Test
    public void testGetMessageFromSecondClient() {
        producer.sendLog(2);
        sleep();
        Assertions.assertEquals(0L, countDownLatch.getCount());
    }

    private void sleep() {
        try {
            countDownLatch.await(180, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * a producer for test
 */
class LoggerProducerTest {

    private KafkaTemplate<String, String> kafkaTemplate;

    public LoggerProducerTest(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendLog(int... partitions) {
        for (int i = 0; i < LoggerConsumerImplIT.QUANTITY; i++) {
            int indexOfPartition = i % partitions.length;
            String key = "key" + i;
            String message = "message" + i;
            Collection<Header> headers = new ArrayList<>();
            headers.add(new RecordHeader("LoggerLevel", "DEBUG".getBytes()));
            headers.add(new RecordHeader("Class", LoggerProducerTest.class.getName().getBytes()));
            ProducerRecord<String, String> record = new ProducerRecord<>(KafkaTopics.LOGGER, partitions[indexOfPartition], key, message, headers);
            ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(record);
            result.addCallback(result1 -> System.out.println("Kafka logger result: " + result1),
                    err -> System.err.println("Kafka logger error: " + err));
        }
    }
}

/**
 * a producer configuration for tests
 */
@Configuration
class LoggerProducerTestConfig {

    @Bean
    public LoggerProducerTest buildLoggerProducerTest() {
        return new LoggerProducerTest(kafkaTemplate());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        kafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
        return kafkaTemplate;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(getProperties());
    }

    private Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 32768);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9007,localhost:9008,localhost:9009");
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 10485760);
        properties.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 180000);
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1000);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return properties;
    }
}