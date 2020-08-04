package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.Contract;

import java.io.Serializable;

public interface ContractRepository<ID extends Serializable> extends CommonRepository<Contract,ID> {
}
