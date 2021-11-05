package com.myenglish.aws;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RDSConfig.class)
@PropertySource("classpath:com/myenglish/aws/aws-config.properties")
@ActiveProfiles("aws")
@Disabled("Commit this annotation after entering aws credentials")
public class RDSConfigTest {

    @Autowired
    DataSource dataSource;

    @Test
    public void setupDataSourceTest() {
        Assertions.assertNotNull(dataSource);
    }
}
