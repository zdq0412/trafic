package com.jxqixin.trafic.service;

import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.SafetyProductionCost;

public interface ISafetyProductionCostService extends ICommonService<SafetyProductionCost> {
    /**
     * 根据企业和年份查找安全生产费用
     * @param org
     * @param intYear
     * @return
     */
    SafetyProductionCost findByOrgAndYear(Org org, int intYear);
}
