package com.myenglish.kafka.logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {LoggerProducerWithoutKafkaConfig.class})
@ActiveProfiles("withoutKafka")
public class LoggerProducerWithoutKafkaTest {

    @Autowired
    LoggerProducer loggerProducer;

    @Test
    public void testContext() {
        Assertions.assertTrue(loggerProducer instanceof LoggerProducerWithoutKafka);
    }


}
