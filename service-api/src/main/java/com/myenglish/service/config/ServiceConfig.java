package com.myenglish.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"com.myenglish.service"})
@PropertySource("classpath:service-api.properties")
public class ServiceConfig {
}
