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
@ContextConfiguration(classes = WebLocalConfig.class)
@ActiveProfiles(profiles = {"local", "withoutKafka"})
public class WebLocalConfigTest {

    @Autowired
    DataSource dataSource;
    @Autowired
    PostgreSQLDBConfig postgreSQL10Config;
    @Autowired(required = false)
    RDSConfig rdsConfig;
    @Autowired
    WordService wordService;

    @Test
    public void setupWebConfigurationTest() {
        Assertions.assertNotNull(dataSource);
        Assertions.assertNotNull(wordService);
        try {
            Assertions.assertEquals("postgres", dataSource.getConnection().getMetaData().getUserName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull(postgreSQL10Config);
        Assertions.assertNull(rdsConfig);
    }
}
