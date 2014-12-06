package com.ccsi.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
    "com.sjw.app.service"
}, repositoryImplementationPostfix = "CustomImpl")
@ComponentScan(basePackages = {
    "com.sjw.app"
})
public class SjwParasiteConfig {

}
