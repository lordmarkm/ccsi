package com.ccsi.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.ccsi.app")
@EnableTransactionManagement
public class CcsiCoreConfig {

}
