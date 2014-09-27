package com.ccsi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author mbmartinez
 */
@Configuration
public class CcsiConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
