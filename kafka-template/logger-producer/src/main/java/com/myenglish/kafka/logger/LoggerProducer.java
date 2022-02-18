package com.myenglish.kafka.logger;

import org.apache.kafka.common.header.Header;

import java.util.Collection;

/**
 * is a custom logger which uses Apache Kafka to send log message
 */
public interface LoggerProducer {

    /**
     * Send log message
     *
     * @param message - value of a record
     */
    void sendLog(String message);

    /**
     * Send log message as a value with key
     *
     * @param message value of a record
     * @param key     key of a record
     */
    void sendLog(String message, String key);

    /**
     * Send log message as a value with key and headers
     *
     * @param message value of a record
     * @param key     key of a record
     * @param headers headers of a record
     */
    void sendLog(String message, String key, Collection<Header> headers);

    /**
     * Send log message with {@link LoggerLevel} TRACE logger level
     *
     * @param message - value of a record
     */
    void trace(String message);

    /**
     * Send log message as a value with key and {@link LoggerLevel} TRACE logger level
     *
     * @param message value of a record
     * @param key     key of a record
     */
    void trace(String message, String key);

    /**
     * Send log message as a value with key, headers and {@link LoggerLevel} TRACE logger level
     *
     * @param message value of a record
     * @param key     key of a record
     * @param headers headers of a record
     */
    void trace(String message, String key, Collection<Header> headers);

    /**
     * Send log message with {@link LoggerLevel} DEBUG logger level
     *
     * @param message - value of a record
     */
    void debug(String message);

    /**
     * Send log message as a value with key and {@link LoggerLevel} DEBUG logger level
     *
     * @param message value of a record
     * @param key     key of a record
     */
    void debug(String message, String key);

    /**
     * Send log message as a value with key, headers and {@link LoggerLevel} DEBUG logger level
     *
     * @param message value of a record
     * @param key     key of a record
     * @param headers headers of a record
     */
    void debug(String message, String key, Collection<Header> headers);

    /**
     * Send log message with {@link LoggerLevel} INFO logger level
     *
     * @param message - value of a record
     */
    void info(String message);

    /**
     * Send log message as a value with key and {@link LoggerLevel} INFO logger level
     *
     * @param message value of a record
     * @param key     key of a record
     */
    void info(String message, String key);

    /**
     * Send log message as a value with key, headers and {@link LoggerLevel} INFO logger level
     *
     * @param message value of a record
     * @param key     key of a record
     * @param headers headers of a record
     */
    void info(String message, String key, Collection<Header> headers);

    /**
     * Send log message with {@link LoggerLevel} WARN logger level
     *
     * @param message - value of a record
     */
    void warn(String message);

    /**
     * Send log message as a value with key and {@link LoggerLevel} WARN logger level
     *
     * @param message value of a record
     * @param key     key of a record
     */
    void warn(String message, String key);

    /**
     * Send log message as a value with key, headers and {@link LoggerLevel} WARN logger level
     *
     * @param message value of a record
     * @param key     key of a record
     * @param headers headers of a record
     */
    void warn(String message, String key, Collection<Header> headers);

    /**
     * Send log message with {@link LoggerLevel} ERROR logger level
     *
     * @param message - value of a record
     */
    void error(String message);

    /**
     * Send log message as a value with key and {@link LoggerLevel} ERROR logger level
     *
     * @param message value of a record
     * @param key     key of a record
     */
    void error(String message, String key);

    /**
     * Send log message as a value with key, headers and {@link LoggerLevel} ERROR logger level
     *
     * @param message value of a record
     * @param key     key of a record
     * @param headers headers of a record
     */
    void error(String message, String key, Collection<Header> headers);
}
