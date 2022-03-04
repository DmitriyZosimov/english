package com.myenglish.web.config;

import com.myenglish.dao.config.DaoHibernateConfig;
import com.myenglish.kafka.logger.LoggerConsumerConfig;
import com.myenglish.kafka.logger.LoggerProducerConfig;
import com.myenglish.kafka.logger.LoggerProducerWithoutKafkaConfig;
import com.myenglish.localdb.PostgreSQLDBConfig;
import com.myenglish.service.config.ServiceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan(basePackages = {"com.myenglish.web"})
@Import({ServiceConfig.class, DaoHibernateConfig.class, PostgreSQLDBConfig.class,
        LoggerProducerConfig.class, LoggerConsumerConfig.class, LoggerProducerWithoutKafkaConfig.class})
@Profile("local")
public class WebLocalConfig {
}
