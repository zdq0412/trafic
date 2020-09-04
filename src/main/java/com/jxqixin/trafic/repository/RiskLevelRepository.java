package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.RiskLevel;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;

public interface RiskLevelRepository<ID extends Serializable> extends CommonRepository<RiskLevel,ID> {
    /**
     * 根据风险度值查找风险等级
     * @param criterion 风险度值
     * @return
     */
    @Query(nativeQuery = true,value = "select * from m060_risk_level where ?1>=lowerLimit and ?1<=upperLimit ")
    RiskLevel findByCriterion(int criterion);
}
