package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.SecurityCheckTemplate;

import java.io.Serializable;

public interface SecurityCheckTemplateRepository<ID extends Serializable> extends CommonRepository<SecurityCheckTemplate,ID> {
    SecurityCheckTemplate findByName(String name);
}
