package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.SafetyProductionCostPlan;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface SafetyProductionCostPlanRepository<ID extends Serializable> extends CommonRepository<SafetyProductionCostPlan,ID> {
    @Query("select s from SafetyProductionCostPlan s where s.safetyProductionCost.org.id=?1 and s.safetyProductionCost.safetyYear=?2")
    List<SafetyProductionCostPlan> findAllByOrgId(String id,int year);
}
