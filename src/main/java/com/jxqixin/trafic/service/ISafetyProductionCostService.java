package com.jxqixin.trafic.service;

import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.SafetyProductionCost;
import com.jxqixin.trafic.model.SafetyProductionCostPlan;
import com.jxqixin.trafic.vo.CostTotalVo;
import com.jxqixin.trafic.vo.SafetyProductionCostVo;

import java.util.Date;
import java.util.List;

public interface ISafetyProductionCostService extends ICommonService<SafetyProductionCost> {
    /**
     * 根据企业和年份查找安全生产费用
     * @param org
     * @param intYear
     * @return
     */
    SafetyProductionCost findByOrgAndYear(Org org, int intYear);
    /**
     * 更新安全生产费用和计划
     * @param cost
     * @param plans
     */
    void updateCostAndPlans(SafetyProductionCost cost, List<SafetyProductionCostPlan> plans);
    /**
     * 按日期统计
     * @param org
     * @param d
     * @param type  year or month
     * @return
     */
    SafetyProductionCostVo findByOrgAndDate(Org org, Date d, String type);
    /**
     * 按年度对安全生产费用进行汇总
     * @param org
     * @param year
     * @return
     */
    CostTotalVo findTotal(Org org, int year);
}
