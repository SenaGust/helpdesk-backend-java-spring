package com.senagust.helpdesk.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure().dataSource(dataSource).locations("classpath:db/migration").load();
    }

    @Bean
    public static BeanFactoryPostProcessor flywayBeforeJpaProcessor() {
        return beanFactory -> {
            BeanDefinition jpaFactory = beanFactory.getBeanDefinition("entityManagerFactory");
            jpaFactory.setDependsOn("flyway");
        };
    }
}
