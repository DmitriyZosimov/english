package com.myenglish.kafka.logger;

import org.apache.kafka.common.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Load if apache kafka isn't enabled
 */
public class LoggerProducerWithoutKafka implements LoggerProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerProducerWithoutKafka.class);

    @Override
    public void sendLog(String message, Class<?> clazz) {
        LOGGER.info(message);
    }

    @Override
    public void sendLog(String message, Class<?> clazz, String key) {
        LOGGER.info(message);
    }

    @Override
    public void sendLog(String message, Class<?> clazz, String key, Collection<Header> headers) {
        LOGGER.info(message);
    }

    @Override
    public void trace(String message, Class<?> clazz) {
        LOGGER.trace(message);
    }

    @Override
    public void trace(String message, Class<?> clazz, String key) {
        LOGGER.trace(message);
    }

    @Override
    public void trace(String message, Class<?> clazz, String key, Collection<Header> headers) {
        LOGGER.trace(message);
    }

    @Override
    public void debug(String message, Class<?> clazz) {
        LOGGER.debug(message);
    }

    @Override
    public void debug(String message, Class<?> clazz, String key) {
        LOGGER.debug(message);
    }

    @Override
    public void debug(String message, Class<?> clazz, String key, Collection<Header> headers) {
        LOGGER.debug(message);
    }

    @Override
    public void info(String message, Class<?> clazz) {
        LOGGER.info(message);
    }

    @Override
    public void info(String message, Class<?> clazz, String key) {
        LOGGER.info(message);
    }

    @Override
    public void info(String message, Class<?> clazz, String key, Collection<Header> headers) {
        LOGGER.info(message);
    }

    @Override
    public void warn(String message, Class<?> clazz) {
        LOGGER.warn(message);
    }

    @Override
    public void warn(String message, Class<?> clazz, String key) {
        LOGGER.warn(message);
    }

    @Override
    public void warn(String message, Class<?> clazz, String key, Collection<Header> headers) {
        LOGGER.warn(message);
    }

    @Override
    public void error(String message, Class<?> clazz) {
        LOGGER.error(message);
    }

    @Override
    public void error(String message, Class<?> clazz, String key) {
        LOGGER.error(message);
    }

    @Override
    public void error(String message, Class<?> clazz, String key, Collection<Header> headers) {
        LOGGER.error(message);
    }
}
