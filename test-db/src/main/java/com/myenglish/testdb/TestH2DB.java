package com.myenglish.testdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class TestH2DB {

    private static Logger LOGGER = LoggerFactory.getLogger(TestH2DB.class);

    @Bean
    public DataSource dataSource() {
        try {
            EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
            DataSource dataSource =  dbBuilder.setType(EmbeddedDatabaseType.H2)
                    .addScript("db/h2/schema.sql")
                    .addScript("db/h2/data.sql")
                    .addScript("db/h2/data-verb.sql")
                    .build();
            LOGGER.info("Embedded DataSource was initialized");
            return dataSource;
        } catch (Exception e) {
            LOGGER.error("Embedded test DataSource was not created. ", e);
            return null;
        }
    }
}
