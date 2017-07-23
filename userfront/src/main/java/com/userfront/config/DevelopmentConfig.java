package com.userfront.config;

import org.apache.catalina.servlets.WebdavServlet;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by root on 01/07/17.
 */
@Configuration
@Profile("dev")
@EnableJpaRepositories(basePackages = "com.userfront.backend.repositories")
@EntityScan(basePackages = "com.userfront.backend.domain")
//gets the data info from the file in local directory
@PropertySource(value = "file:///${user.home}/.devopsbank/application-dev.properties", ignoreResourceNotFound = true)
public class DevelopmentConfig {

    @Bean
    public ServletRegistrationBean h2ConsoleServiceRegistration(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new WebdavServlet());
        bean.addUrlMappings("/console/*");

        return bean;
    }
}
