package com.myenglish.kafka.logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {LoggerProducerConfig.class})
@Disabled
public class ContextTest {

    @Autowired
    LoggerProducerImpl producer;

    @Test
    public void setupTest() {
        Assertions.assertNotNull(producer);
    }
}
