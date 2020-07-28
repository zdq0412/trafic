package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.DangerGoodsCheckTemplate;

import java.io.Serializable;

public interface DangerGoodsCheckTemplateRepository<ID extends Serializable> extends CommonRepository<DangerGoodsCheckTemplate,ID> {
    DangerGoodsCheckTemplate findByName(String name);
}
