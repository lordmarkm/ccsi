package com.ccsi.app.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ccsi.app.entity.Variable;
import com.ccsi.app.service.custom.VariableServiceCustom;

/**
 * @author mbmartinez
 */
public interface VariableService extends VariableServiceCustom, JpaRepository<Variable, Long> {

    List<Variable> findByTenant_id(Long tenantId);

}
