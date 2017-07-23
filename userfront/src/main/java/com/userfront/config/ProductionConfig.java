package com.userfront.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by root on 01/07/17.
 */
@Configuration
@Profile("prod")
@EnableJpaRepositories(basePackages = "com.userfront.backend.repositories") // Scan all JPA repositories
@EntityScan(basePackages = "com.userfront.backend.domain") // Scan all JPA entities
@EnableTransactionManagement // This enables annotation based transaction management (JPA transaction is managed by default)
@PropertySource("file:///${user.home}/.devopsbank/application-prod.properties")//gets the data info from the file in local directory
public class ProductionConfig {
}
