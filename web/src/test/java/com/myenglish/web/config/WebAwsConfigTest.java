package com.myenglish.web.config;

import com.myenglish.aws.RDSConfig;
import com.myenglish.localdb.PostgreSQLDBConfig;
import com.myenglish.service.WordService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.SQLException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = WebConfig.class)
@ActiveProfiles("aws")
@Disabled("Commit this annotation after entering aws credentials")
public class WebAwsConfigTest {

    @Autowired
    DataSource dataSource;
    @Autowired
    RDSConfig rdsConfig;
    @Autowired(required = false)
    PostgreSQLDBConfig postgreSQLDBConfig;
    @Autowired
    WordService wordService;

    @Test
    public void setupDataSourceConfigTest() {
        Assertions.assertNotNull(dataSource);
        Assertions.assertNotNull(wordService);
        try {
            Assertions.assertEquals("english", dataSource.getConnection().getMetaData().getUserName());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Assertions.assertNotNull(rdsConfig);
        Assertions.assertNotNull(rdsConfig);
        Assertions.assertNull(postgreSQLDBConfig);
    }
}
