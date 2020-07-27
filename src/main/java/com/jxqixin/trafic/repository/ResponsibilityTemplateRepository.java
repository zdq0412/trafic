package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.ResponsibilityTemplate;

import java.io.Serializable;

public interface ResponsibilityTemplateRepository<ID extends Serializable> extends CommonRepository<ResponsibilityTemplate,ID> {
    ResponsibilityTemplate findByName(String name);
}
