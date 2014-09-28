package com.ccsi.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baldy.commons.security.services.BaldyCommonsSecurityServicesMarker;

@Configuration
@PropertySource("classpath:db.properties")
@EnableJpaRepositories(basePackageClasses = BaldyCommonsSecurityServicesMarker.class)
@EnableTransactionManagement
public class SecurityPersistenceConfig {

}