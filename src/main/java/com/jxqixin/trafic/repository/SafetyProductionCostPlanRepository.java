package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.SafetyProductionCostPlan;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface SafetyProductionCostPlanRepository<ID extends Serializable> extends CommonRepository<SafetyProductionCostPlan,ID> {
    @Query("select s from SafetyProductionCostPlan s where s.safetyProductionCost.org.id=?1 and s.safetyProductionCost.safetyYear=?2")
    List<SafetyProductionCostPlan> findAllByOrgId(String id,int year);
    /**
     * 根据生产费用ID查找生产费用计划
     * @param safetyProductionCostId
     * @return
     */
    @Query("select plan  from SafetyProductionCostPlan plan where plan.safetyProductionCost.id=?1 ")
    List<SafetyProductionCostPlan> findBySafetyProductionCostId(String safetyProductionCostId);
}
