package com.myenglish.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.context.config.annotation.EnableContextCredentials;
import org.springframework.cloud.aws.context.config.annotation.EnableContextRegion;
import org.springframework.cloud.aws.jdbc.config.annotation.EnableRdsInstance;
import org.springframework.cloud.aws.jdbc.config.annotation.RdsInstanceConfigurer;
import org.springframework.cloud.aws.jdbc.datasource.DataSourceFactory;
import org.springframework.cloud.aws.jdbc.datasource.TomcatJdbcDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:com/myenglish/aws/aws-config.properties")
@EnableContextRegion(region = "${cloud.aws.rds.region}")
@EnableRdsInstance(dbInstanceIdentifier = "${cloud.aws.rds.dbInstanceIdentifier}",
        password = "${cloud.aws.rds.password}",
        username = "${cloud.aws.rds.username}",
        readReplicaSupport = true, databaseName = "${cloud.aws.rds.databaseName}")
@EnableContextCredentials(accessKey = "${cloud.aws.accessKey}", secretKey = "${cloud.aws.secretKey}")
@Profile("aws")
public class RDSConfig {

    @Value("${db.pool.initialSize}")
    private int initialSize;
    @Value("${db.pool.maxActive}")
    private int maxActive;
    @Value("${db.pool.maxIdle}")
    private int maxIdle;
    @Value("${db.pool.minIdle}")
    private int minIdle;
    @Value("${db.pool.maxWait}")
    private int maxWait;
    @Value("${db.pool.validationQuery}")
    private String validationQuery;
    @Value("${db.pool.validationQueryTimeout}")
    private int validationQueryTimeout;
    @Value("${db.pool.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${db.pool.testOnReturn}")
    private boolean testOnReturn;
    @Value("${db.pool.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${db.pool.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${db.pool.numTestsPerEvictionRun}")
    private int numTestsPerEvictionRun;

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public RdsInstanceConfigurer instanceConfigurer() {
        return new RdsInstanceConfigurer() {
            @Override
            public DataSourceFactory getDataSourceFactory() {
                TomcatJdbcDataSourceFactory factory = new TomcatJdbcDataSourceFactory();
                factory.setInitialSize(initialSize);
                factory.setMaxActive(maxActive);
                factory.setMaxIdle(maxIdle);
                factory.setMinIdle(minIdle);
                factory.setMaxWait(maxWait);
                factory.setValidationQuery(validationQuery);
                factory.setValidationQueryTimeout(validationQueryTimeout);
                factory.setTestOnBorrow(testOnBorrow);
                factory.setTestOnReturn(testOnReturn);
                factory.setTestWhileIdle(testWhileIdle);
                factory.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
                factory.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
                return factory;
            }
        };
    }

}
