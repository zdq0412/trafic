package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.Responsibility;

import java.io.Serializable;

public interface ResponsibilityRepository<ID extends Serializable> extends CommonRepository<Responsibility,ID> {
    Responsibility findByName(String name);
}
