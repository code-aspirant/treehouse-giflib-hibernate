package com.teamtreehouse.giflib.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;

@Configuration
@PropertySource("app.properties") // Tell spring where the properties file is
public class DataConfig {

    // Get access to the properties values
    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        Resource config = new ClassPathResource("hibernate.cfg.xml");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setConfigLocation(config);
        // Tell spring where to find entity mappings
        // This also avoids hard-coding values in your source code
        sessionFactory.setPackagesToScan(env.getProperty("giflib.entity.package"));
        sessionFactory.setDataSource(dataSource());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        // Set Driver class name
        dataSource.setDriverClassName(env.getProperty("giflib.db.driver"));
        // Set URL
        dataSource.setUrl(env.getProperty("giflib.db.url"));
        // Set username & password
        dataSource.setUsername(env.getProperty("giflib.db.username"));
        dataSource.setPassword(env.getProperty("giflib.db.password"));
        return dataSource;
    }
}
