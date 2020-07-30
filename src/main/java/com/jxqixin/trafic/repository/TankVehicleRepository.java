package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.TankVehicle;

import java.io.Serializable;

public interface TankVehicleRepository<ID extends Serializable> extends CommonRepository<TankVehicle,ID> {
}
