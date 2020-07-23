package com.jxqixin.trafic.service;

import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.SafetyProductionCostPlan;

import java.util.List;

public interface ISafetyProductionCostPlanService extends ICommonService<SafetyProductionCostPlan> {
    /**
     * 查询当前企业下的所有安全生产费用计划
     * @param org
     * @return
     */
    List<SafetyProductionCostPlan> findAllPlans(Org org);
}
