package com.jxqixin.trafic.repository;
import com.jxqixin.trafic.model.SafetyProductionCost;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
public interface SafetyProductionCostRepository<ID extends Serializable> extends CommonRepository<SafetyProductionCost,ID> {
    /**
     * 根据企业ID和年份查找安全生产费用
     * @param id
     * @param intYear
     * @return
     */
    @Query("select s from SafetyProductionCost s where s.org.id=?1 and s.safetyYear=?2")
    SafetyProductionCost findByOrgIdAndYear(String id, int intYear);
}
