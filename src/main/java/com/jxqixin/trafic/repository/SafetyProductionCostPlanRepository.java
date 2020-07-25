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
    /**
     * 查找安全生产费用计划每月汇总
     * @param orgId
     * @param year
     * @return
     */
    @Query(nativeQuery = true,value = "select p.name name,sum(case when month(billingDate)=1 then sumOfMoney else 0 end) onemonth," +
            "sum(case when month(billingDate)=2 then sumOfMoney else 0 end) twomonth," +
            "sum(case when month(billingDate)=3 then sumOfMoney else 0 end) threemonth," +
            "sum(case when month(billingDate)=4 then sumOfMoney else 0 end) fourmonth," +
            "sum(case when month(billingDate)=5 then sumOfMoney else 0 end) fivemonth," +
            "sum(case when month(billingDate)=6 then sumOfMoney else 0 end) sixmonth," +
            "sum(case when month(billingDate)=7 then sumOfMoney else 0 end) sevenmonth," +
            "sum(case when month(billingDate)=8 then sumOfMoney else 0 end) eightmonth," +
            "sum(case when month(billingDate)=9 then sumOfMoney else 0 end) ninemonth," +
            "sum(case when month(billingDate)=10 then sumOfMoney else 0 end) tenmonth," +
            "sum(case when month(billingDate)=11 then sumOfMoney else 0 end) elevenmonth," +
            "sum(case when month(billingDate)=12 then sumOfMoney else 0 end) twelvemonth ,p.id id " +
            "  from m019_safety_production_cost_plan p " +
            " left join m019_safety_production_cost_plan_detail d on " +
            " p.id = d.plan_id where year(billingDate)=?2 and d.org_id=?1" +
            " group by p.name")
    List<Object[]> findTotalUse(String orgId, int year);
}
