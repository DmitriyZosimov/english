package com.myenglish.web.config;

import com.myenglish.dao.config.DaoHibernateConfig;
import com.myenglish.localdb.PostgreSQLDBConfig;
import com.myenglish.service.config.ServiceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan(basePackages = {"com.myenglish.web"})
@Import({ServiceConfig.class, DaoHibernateConfig.class, PostgreSQLDBConfig.class})
@Profile("local")
public class WebLocalConfig {
}
