package com.myenglish.dao.config;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan("com.myenglish.dao")
@EnableTransactionManagement
@PropertySource("classpath:dao-hibernate.properties")
public class DaoHibernateConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(DaoHibernateConfig.class);

    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${hibernate.format_sql}")
    private Boolean formatSql;
    @Value("${hibernate.use_sql_comments}")
    private Boolean useSqlComments;
    @Value("${hibernate.show_sql}")
    private Boolean show_sql;
    @Value("${hibernate.max_fetch_depth}")
    private int maxFetchDepth;
    @Value("${hibernate.jdbc.batch_size}")
    private int batchSize;
    @Value("${hibernate.jdbc.fetch_size}")
    private int fetchSize;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) throws IOException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan("com.myenglish.model");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        sessionFactoryBean.afterPropertiesSet();
        return sessionFactoryBean.getObject();
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", dialect);
        hibernateProperties.put("hibernate.format_sql", formatSql);
        hibernateProperties.put("hibernate.use_sql_comments", useSqlComments);
        hibernateProperties.put("hibernate.show_sql", show_sql);
        hibernateProperties.put("hibernate.max_fetch_depth", maxFetchDepth);
        hibernateProperties.put("hibernate.jdbc.batch_size", batchSize);
        hibernateProperties.put("hibernate.jdbc.fetch_size", fetchSize);
        hibernateProperties.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        return hibernateProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}
