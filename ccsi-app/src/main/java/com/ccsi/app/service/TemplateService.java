package com.ccsi.app.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ccsi.app.entity.Template;

/**
 * @author mbmartinez
 */
public interface TemplateService extends JpaRepository<Template, Long> {

}
