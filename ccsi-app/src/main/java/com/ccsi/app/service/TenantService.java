package com.ccsi.app.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ccsi.app.entity.Tenant;
import com.ccsi.app.service.custom.TenantServiceCustom;

/**
 * @author mbmartinez
 */
public interface TenantService extends TenantServiceCustom, JpaRepository<Tenant, Long> {

    List<Tenant> findByOwner_username(String name);

}
