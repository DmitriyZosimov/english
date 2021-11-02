package com.english.aws;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RDSConfig.class)
public class RDSConfigTest {

    @Autowired
    DataSource dataSource;

    @Test
    public void setupDataSourceTest() {
        Assertions.assertNotNull(dataSource);
    }
}
