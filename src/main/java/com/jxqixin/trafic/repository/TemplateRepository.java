package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.Template;

import java.io.Serializable;

public interface TemplateRepository<ID extends Serializable> extends CommonRepository<Template,ID> {
    Template findByName(String name);
}
