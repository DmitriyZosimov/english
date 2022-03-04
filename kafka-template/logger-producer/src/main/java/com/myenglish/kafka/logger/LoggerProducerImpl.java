package com.myenglish.kafka.logger;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.Collection;

@Component
@Profile("kafka")
public class LoggerProducerImpl implements LoggerProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    public LoggerProducerImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendLog(String message, Class<?> clazz) {
        this.sendLog(message, clazz, null, null);
    }

    @Override
    public void sendLog(String message, Class<?> clazz, String key) {

        this.sendLog(message, clazz, key, null);
    }

    @Override
    public void sendLog(String message, Class<?> clazz, String key, Collection<Header> headers) {
        headers = addClassToHeaders(headers, clazz);
        ProducerRecord<String, String> record = new ProducerRecord<>(KafkaTopics.LOGGER, null, key, message, headers);
        ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(record);
        result.addCallback(result1 -> System.out.println("Kafka logger result: " + result1),
                err -> System.err.println("Kafka logger error: " + err));
        kafkaTemplate.flush();
    }

    @Override
    public void trace(String message, Class<?> clazz) {
        this.trace(message, clazz, null, null);
    }

    @Override
    public void trace(String message, Class<?> clazz, String key) {
        this.trace(message, clazz, key, null);
    }

    @Override
    public void trace(String message, Class<?> clazz, String key, Collection<Header> headers) {
        headers = checkLoggerLevelInHeaders(headers, LoggerLevel.TRACE);
        this.sendLog(message, clazz, key, headers);
    }

    @Override
    public void debug(String message, Class<?> clazz) {
        this.debug(message, clazz, null, null);
    }

    @Override
    public void debug(String message, Class<?> clazz, String key) {
        this.debug(message, clazz, key, null);
    }

    @Override
    public void debug(String message, Class<?> clazz, String key, Collection<Header> headers) {
        headers = checkLoggerLevelInHeaders(headers, LoggerLevel.DEBUG);
        this.sendLog(message, clazz, key, headers);
    }

    @Override
    public void info(String message, Class<?> clazz) {
        this.info(message, clazz, null, null);
    }

    @Override
    public void info(String message, Class<?> clazz, String key) {
        this.info(message, clazz, key, null);
    }

    @Override
    public void info(String message, Class<?> clazz, String key, Collection<Header> headers) {
        headers = checkLoggerLevelInHeaders(headers, LoggerLevel.INFO);
        this.sendLog(message, clazz, key, headers);
    }

    @Override
    public void warn(String message, Class<?> clazz) {
        this.warn(message, clazz, null, null);
    }

    @Override
    public void warn(String message, Class<?> clazz, String key) {
        this.warn(message, clazz, key, null);
    }

    @Override
    public void warn(String message, Class<?> clazz, String key, Collection<Header> headers) {
        headers = checkLoggerLevelInHeaders(headers, LoggerLevel.WARN);
        this.sendLog(message, clazz, key, headers);
    }

    @Override
    public void error(String message, Class<?> clazz) {
        this.error(message, clazz, null, null);
    }

    @Override
    public void error(String message, Class<?> clazz, String key) {
        this.error(message, clazz, key, null);
    }

    @Override
    public void error(String message, Class<?> clazz, String key, Collection<Header> headers) {
        headers = checkLoggerLevelInHeaders(headers, LoggerLevel.ERROR);
        this.sendLog(message, clazz, key, headers);
    }

    private Collection<Header> checkLoggerLevelInHeaders(Collection<Header> headers, LoggerLevel loggerLevel) {
        return addHeader(headers, "LoggerLevel", loggerLevel.name());
    }

    private Collection<Header> addClassToHeaders(Collection<Header> headers, Class<?> clazz) {
        return addHeader(headers, "Class", clazz.getName());
    }

    private Collection<Header> addHeader(Collection<Header> headers, String key, String value) {
        RecordHeader header;
        if (headers == null) {
            headers = new ArrayList<>();
            headers.add(new RecordHeader(key, value.getBytes()));
        } else if (!headers.contains(header = new RecordHeader(key, value.getBytes()))) {
            headers.add(header);
        }
        return headers;
    }
}
