package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.TrainingTemplate;

import java.io.Serializable;

public interface TrainingTemplateRepository<ID extends Serializable> extends CommonRepository<TrainingTemplate,ID> {
    TrainingTemplate findByName(String name);
}
