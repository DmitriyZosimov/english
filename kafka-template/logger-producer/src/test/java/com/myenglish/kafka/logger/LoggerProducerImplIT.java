package com.myenglish.kafka.logger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@EmbeddedKafka(partitions = 1, topics = "logger", brokerProperties = {"listeners=PLAINTEXT://localhost:9089", "port=9089"})
@TestPropertySource(locations = {"classpath:logger-producer-test.properties"})
@ContextConfiguration(classes = {LoggerProducerConfig.class, TestConsumerConfig.class})
@ActiveProfiles("kafka")
public class LoggerProducerImplIT {

    @Autowired
    private LoggerProducer producer;
    @Autowired
    private TestConsumer consumer;

    private static final String MESSAGE = "MESSAGE";
    private static final String KEY = "KEY";
    private static final Class<?> CLASS = LoggerProducerImplIT.class;
    private ConsumerRecord<String, String> record;

    @AfterEach
    public void testCommonInfoFromRecord() {
        if (record != null) {
            assertEquals(KafkaTopics.LOGGER, record.topic());
            assertEquals(MESSAGE, record.value());
            String classNameFromHeader = new String(record.headers().lastHeader("Class").value());
            assertEquals(CLASS.getName(), classNameFromHeader);

            System.out.println("Record: " + record);
            record = null;
        }
        System.out.println("============================================================");
    }

    @DirtiesContext
    @Test
    public void setupTest() {
        Assertions.assertNotNull(producer);
    }

    @DirtiesContext
    @Test
    public void sendLog_WithOneParam() {
        producer.sendLog(MESSAGE, CLASS);
        sleep(consumer);
        record = consumer.getRecord();
    }

    @DirtiesContext
    @Test
    public void sendLog_WithTwoParams() {
        producer.sendLog(MESSAGE, CLASS, KEY);
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
    }

    @DirtiesContext
    @Test
    public void sendLog_WithThreeParams() {
        producer.sendLog(MESSAGE, CLASS, KEY, null);
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
        assertNotNull(record.headers());
    }

    @DirtiesContext
    @Test
    public void trace_WithOneParam() {
        producer.trace(MESSAGE, CLASS);
        sleep(consumer);
        record = consumer.getRecord();
    }

    @DirtiesContext
    @Test
    public void trace_WithTwoParams() {
        producer.trace(MESSAGE, CLASS, KEY);
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
    }

    @DirtiesContext
    @Test
    public void trace_WithThreeParams_WhenHeadersExist() {
        producer.trace(MESSAGE, CLASS, KEY, buildHeaders(LoggerLevel.TRACE));
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
        assertNotNull(record.headers());
    }

    @DirtiesContext
    @Test
    public void trace_WithThreeParams_WhenHeadersNotExist() {
        producer.trace(MESSAGE, CLASS, KEY, null);
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
        String logLevel = new String(record.headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.TRACE.name(), logLevel);
    }

    @DirtiesContext
    @Test
    public void trace_WithThreeParams_WhenHeadersNotContainLoggerLevel() {
        producer.trace(MESSAGE, CLASS, KEY, new ArrayList<>());
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
        String logLevel = new String(record.headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.TRACE.name(), logLevel);
    }

    @DirtiesContext
    @Test
    public void debug_WithOneParam() {
        producer.debug(MESSAGE, CLASS);
        sleep(consumer);
        record = consumer.getRecord();
    }

    @DirtiesContext
    @Test
    public void debug_WithTwoParams() {
        producer.debug(MESSAGE, CLASS, KEY);
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
    }

    @DirtiesContext
    @Test
    public void debug_WithThreeParams_WhenHeadersExist() {
        producer.debug(MESSAGE, CLASS, KEY, buildHeaders(LoggerLevel.DEBUG));
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
        assertNotNull(record.headers());
    }

    @DirtiesContext
    @Test
    public void debug_WithThreeParams_WhenHeadersNotExist() {
        producer.debug(MESSAGE, CLASS, KEY, null);
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
        String logLevel = new String(record.headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.DEBUG.name(), logLevel);
    }

    @DirtiesContext
    @Test
    public void debug_WithThreeParams_WhenHeadersNotContainLoggerLevel() {
        producer.debug(MESSAGE, CLASS, KEY, new ArrayList<>());
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
        String logLevel = new String(record.headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.DEBUG.name(), logLevel);
    }

    @DirtiesContext
    @Test
    public void info_WithOneParam() {
        producer.info(MESSAGE, CLASS);
        sleep(consumer);
        record = consumer.getRecord();
    }

    @DirtiesContext
    @Test
    public void info_WithTwoParams() {
        producer.info(MESSAGE, CLASS, KEY);
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
    }

    @DirtiesContext
    @Test
    public void info_WithThreeParams_WhenHeadersExist() {
        producer.info(MESSAGE, CLASS, KEY, buildHeaders(LoggerLevel.INFO));
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
        assertNotNull(record.headers());
    }

    @DirtiesContext
    @Test
    public void info_WithThreeParams_WhenHeadersNotExist() {
        producer.info(MESSAGE, CLASS, KEY, null);
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
        String logLevel = new String(record.headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.INFO.name(), logLevel);
    }

    @DirtiesContext
    @Test
    public void info_WithThreeParams_WhenHeadersNotContainLoggerLevel() {
        producer.info(MESSAGE, CLASS, KEY, new ArrayList<>());
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
        String logLevel = new String(record.headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.INFO.name(), logLevel);
    }

    @DirtiesContext
    @Test
    public void warn_WithOneParam() {
        producer.warn(MESSAGE, CLASS);
        sleep(consumer);
        record = consumer.getRecord();
    }

    @DirtiesContext
    @Test
    public void warn_WithTwoParams() {
        producer.warn(MESSAGE, CLASS, KEY);
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
    }

    @DirtiesContext
    @Test
    public void warn_WithThreeParams_WhenHeadersExist() {
        producer.warn(MESSAGE, CLASS, KEY, buildHeaders(LoggerLevel.WARN));
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
        assertNotNull(record.headers());
    }

    @DirtiesContext
    @Test
    public void warn_WithThreeParams_WhenHeadersNotExist() {
        producer.warn(MESSAGE, CLASS, KEY, null);
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
        String logLevel = new String(record.headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.WARN.name(), logLevel);
    }

    @DirtiesContext
    @Test
    public void warn_WithThreeParams_WhenHeadersNotContainLoggerLevel() {
        producer.warn(MESSAGE, CLASS, KEY, new ArrayList<>());
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
        String logLevel = new String(record.headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.WARN.name(), logLevel);
    }

    @DirtiesContext
    @Test
    public void error_WithOneParam() {
        producer.error(MESSAGE, CLASS);
        sleep(consumer);
        record = consumer.getRecord();
    }

    @DirtiesContext
    @Test
    public void error_WithTwoParams() {
        producer.error(MESSAGE, CLASS, KEY);
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
    }

    @DirtiesContext
    @Test
    public void error_WithThreeParams_WhenHeadersExist() {
        producer.error(MESSAGE, CLASS, KEY, buildHeaders(LoggerLevel.ERROR));
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
        assertNotNull(record.headers());
    }

    @DirtiesContext
    @Test
    public void error_WithThreeParams_WhenHeadersNotExist() {
        producer.error(MESSAGE, CLASS, KEY, null);
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
        String logLevel = new String(record.headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.ERROR.name(), logLevel);
    }

    @DirtiesContext
    @Test
    public void error_WithThreeParams_WhenHeadersNotContainLoggerLevel() {
        producer.error(MESSAGE, CLASS, KEY, new ArrayList<>());
        sleep(consumer);
        record = consumer.getRecord();
        assertEquals(KEY, record.key());
        String logLevel = new String(record.headers().lastHeader("LoggerLevel").value());
        assertEquals(LoggerLevel.ERROR.name(), logLevel);
    }

    private Collection<Header> buildHeaders(LoggerLevel loggerLevel) {
        Collection<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("LoggerLevel", loggerLevel.name().getBytes()));
        return headers;
    }

    private void sleep(TestConsumer consumer) {
        try {
            consumer.getCountDownLatch().await(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
