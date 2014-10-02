package com.ccsi.app.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ccsi.app.entity.Variable;
import com.ccsi.app.service.custom.VariableServiceCustom;

/**
 * @author mbmartinez
 */
public interface VariableService extends VariableServiceCustom, JpaRepository<Variable, Long> {

    @Deprecated
    List<Variable> findByTenant_id(Long tenantId, Sort sort);

    /**
     * To find a variable "templates" (one associated with a tenant but not with a tenant record), set 
     * recordId to null.
     */
    List<Variable> findByTenant_idAndRecord_id(Long tenantId, Long recordId, Sort sort);

}
