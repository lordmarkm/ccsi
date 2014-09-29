package com.ccsi.app.config;

import static org.dozer.loader.api.FieldsMappingOptions.copyByReference;
import static org.dozer.loader.api.FieldsMappingOptions.oneWay;

import javax.annotation.PostConstruct;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ccsi.app.entity.TenantRecord;
import com.ccsi.commons.dto.tenant.TenantRecordInfo;

@Configuration
public class CcsiSupportConfig {

    @Bean
    public DozerBeanMapper mapper() {
        return new DozerBeanMapper();
    }

    @PostConstruct
    public void init() {
        mapper().addMapping(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(TenantRecord.class, TenantRecordInfo.class)
                    .fields("lastUpdated", "lastUpdated", copyByReference(), oneWay());
            }
        });
    }
}
