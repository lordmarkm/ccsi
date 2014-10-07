package com.ccsi.app.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ccsi.app.entity.Template;
import com.ccsi.app.service.custom.TemplateServiceCustom;

/**
 * @author mbmartinez
 */
public interface TemplateService extends JpaRepository<Template, Long>, TemplateServiceCustom {

    List<Template> findByTenant_id(Long tenantId, Sort sort);
    Template findByTenant_idAndStatus(Long id, String status);

}
