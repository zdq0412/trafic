package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.RuleTemplate;

import java.io.Serializable;

public interface RuleTemplateRepository<ID extends Serializable> extends CommonRepository<RuleTemplate,ID> {
    RuleTemplate findByName(String name);
}
