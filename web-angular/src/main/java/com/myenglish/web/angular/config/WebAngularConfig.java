package com.myenglish.web.angular.config;

import com.myenglish.aws.RDSConfig;
import com.myenglish.dao.config.DaoHibernateConfig;
import com.myenglish.service.config.ServiceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan(basePackages = {"com.myenglish.web.angular"})
@Import({ServiceConfig.class, DaoHibernateConfig.class, RDSConfig.class})
@Profile("aws")
public class WebAngularConfig {
}
