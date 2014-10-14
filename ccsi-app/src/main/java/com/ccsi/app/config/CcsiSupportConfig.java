package com.ccsi.app.config;

import static org.dozer.loader.api.FieldsMappingOptions.copyByReference;
import static org.dozer.loader.api.FieldsMappingOptions.oneWay;

import javax.annotation.PostConstruct;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ccsi.app.entity.Tenant;
import com.ccsi.app.entity.TenantRecord;
import com.ccsi.app.entity.TransactionRecord;
import com.ccsi.commons.dto.tenant.TenantInfo;
import com.ccsi.commons.dto.tenant.TenantRecordInfo;
import com.ccsi.commons.dto.tenant.TransactionRecordInfo;

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
                mapping(Tenant.class, TenantInfo.class)
                    .fields("owner.username", "owner", oneWay())
                    .fields("pushCredits", "pushCredits", oneWay());
                mapping(TenantRecord.class, TenantRecordInfo.class)
                    .fields("lastUpdated", "lastUpdated", copyByReference(), oneWay());
                mapping(TransactionRecord.class, TransactionRecordInfo.class)
                    .fields("transactionDate", "transactionDate", copyByReference())
                    .fields("tenant.name", "tenantName", oneWay());
            }
        });
    }
}
