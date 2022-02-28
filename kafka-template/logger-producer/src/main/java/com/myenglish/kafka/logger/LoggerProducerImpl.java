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
    public void sendLog(String message) {
        this.sendLog(message, null, null);
    }

    @Override
    public void sendLog(String message, String key) {

        this.sendLog(message, key, null);
    }

    @Override
    public void sendLog(String message, String key, Collection<Header> headers) {
        ProducerRecord<String, String> record = new ProducerRecord<>(KafkaTopics.LOGGER, null, key, message, headers);
        ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(record);
        result.addCallback(result1 -> System.out.println("Kafka logger result: " + result1),
                err -> System.err.println("Kafka logger error: " + err));
        kafkaTemplate.flush();
    }

    @Override
    public void trace(String message) {
        this.trace(message, null, null);
    }

    @Override
    public void trace(String message, String key) {
        this.trace(message, key, null);
    }

    @Override
    public void trace(String message, String key, Collection<Header> headers) {
        headers = checkLoggerLevelInHeaders(headers, LoggerLevel.TRACE);
        this.sendLog(message, key, headers);
    }

    @Override
    public void debug(String message) {
        this.debug(message, null, null);
    }

    @Override
    public void debug(String message, String key) {
        this.debug(message, key, null);
    }

    @Override
    public void debug(String message, String key, Collection<Header> headers) {
        headers = checkLoggerLevelInHeaders(headers, LoggerLevel.DEBUG);
        this.sendLog(message, key, headers);
    }

    @Override
    public void info(String message) {
        this.info(message, null, null);
    }

    @Override
    public void info(String message, String key) {
        this.info(message, key, null);
    }

    @Override
    public void info(String message, String key, Collection<Header> headers) {
        headers = checkLoggerLevelInHeaders(headers, LoggerLevel.INFO);
        this.sendLog(message, key, headers);
    }

    @Override
    public void warn(String message) {
        this.warn(message, null, null);
    }

    @Override
    public void warn(String message, String key) {
        this.warn(message, key, null);
    }

    @Override
    public void warn(String message, String key, Collection<Header> headers) {
        headers = checkLoggerLevelInHeaders(headers, LoggerLevel.WARN);
        this.sendLog(message, key, headers);
    }

    @Override
    public void error(String message) {
        this.error(message, null, null);
    }

    @Override
    public void error(String message, String key) {
        this.error(message, key, null);
    }

    @Override
    public void error(String message, String key, Collection<Header> headers) {
        headers = checkLoggerLevelInHeaders(headers, LoggerLevel.ERROR);
        this.sendLog(message, key, headers);
    }

    private Collection<Header> checkLoggerLevelInHeaders(Collection<Header> headers, LoggerLevel loggerLevel) {
        RecordHeader header;
        if (headers == null) {
            headers = new ArrayList<>();
            headers.add(new RecordHeader("LoggerLevel", loggerLevel.name().getBytes()));
        } else if (!headers.contains(header = new RecordHeader("LoggerLevel", loggerLevel.name().getBytes()))) {
            headers.add(header);
        }
        return headers;
    }
}
