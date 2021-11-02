package com.myenglish.web.config;

import com.english.aws.RDSConfig;
import com.myenglish.dao.config.WordDaoHibernateConfig;
import com.myenglish.service.config.WordServiceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.myenglish.web"})
@Import({WordServiceConfig.class, WordDaoHibernateConfig.class, RDSConfig.class})
public class WebConfig {

}
