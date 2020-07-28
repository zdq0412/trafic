package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.TankVehicleTemplate;

import java.io.Serializable;

public interface TankVehicleTemplateRepository<ID extends Serializable> extends CommonRepository<TankVehicleTemplate,ID> {
    TankVehicleTemplate findByName(String name);
}
