package com.myenglish.web.config;

import com.myenglish.aws.RDSConfig;
import com.myenglish.dao.config.DaoHibernateConfig;
import com.myenglish.service.config.ServiceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan(basePackages = {"com.myenglish.web"})
@Import({ServiceConfig.class, DaoHibernateConfig.class, RDSConfig.class})
@Profile("aws")
public class WebConfig {

}
