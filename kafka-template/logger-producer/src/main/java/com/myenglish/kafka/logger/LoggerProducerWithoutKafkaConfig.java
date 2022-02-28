package com.myenglish.kafka.logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Load if apache kafka isn't enabled
 */
@Configuration
@Profile("withoutKafka")
public class LoggerProducerWithoutKafkaConfig {

    @Bean
    public LoggerProducer getLoggerProducerWithoutKafka() {
        return new LoggerProducerWithoutKafka();
    }
}
