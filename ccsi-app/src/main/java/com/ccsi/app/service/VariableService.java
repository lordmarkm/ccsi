package com.ccsi.app.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ccsi.app.entity.TenantVariable;

/**
 * @author mbmartinez
 */
public interface VariableService extends JpaRepository<TenantVariable, Long> {

}
