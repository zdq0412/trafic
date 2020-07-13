package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.Position;

import java.io.Serializable;
public interface PositionRepository<ID extends Serializable> extends CommonRepository<Position,ID> {
}
