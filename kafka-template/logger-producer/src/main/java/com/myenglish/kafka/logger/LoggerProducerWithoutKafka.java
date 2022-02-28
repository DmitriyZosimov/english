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
    public void sendLog(String message) {
        LOGGER.info(message);
    }

    @Override
    public void sendLog(String message, String key) {
        LOGGER.info(message);
    }

    @Override
    public void sendLog(String message, String key, Collection<Header> headers) {
        LOGGER.info(message);
    }

    @Override
    public void trace(String message) {
        LOGGER.trace(message);
    }

    @Override
    public void trace(String message, String key) {
        LOGGER.trace(message);
    }

    @Override
    public void trace(String message, String key, Collection<Header> headers) {
        LOGGER.trace(message);
    }

    @Override
    public void debug(String message) {
        LOGGER.debug(message);
    }

    @Override
    public void debug(String message, String key) {
        LOGGER.debug(message);
    }

    @Override
    public void debug(String message, String key, Collection<Header> headers) {
        LOGGER.debug(message);
    }

    @Override
    public void info(String message) {
        LOGGER.info(message);
    }

    @Override
    public void info(String message, String key) {
        LOGGER.info(message);
    }

    @Override
    public void info(String message, String key, Collection<Header> headers) {
        LOGGER.info(message);
    }

    @Override
    public void warn(String message) {
        LOGGER.warn(message);
    }

    @Override
    public void warn(String message, String key) {
        LOGGER.warn(message);
    }

    @Override
    public void warn(String message, String key, Collection<Header> headers) {
        LOGGER.warn(message);
    }

    @Override
    public void error(String message) {
        LOGGER.error(message);
    }

    @Override
    public void error(String message, String key) {
        LOGGER.error(message);
    }

    @Override
    public void error(String message, String key, Collection<Header> headers) {
        LOGGER.error(message);
    }
}
