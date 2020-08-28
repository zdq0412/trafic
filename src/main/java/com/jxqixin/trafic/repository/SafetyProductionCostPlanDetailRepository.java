package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.SafetyProductionCostPlanDetail;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface SafetyProductionCostPlanDetailRepository<ID extends Serializable> extends CommonRepository<SafetyProductionCostPlanDetail,ID> {
    /**
     * 根据企业ID和年份汇总
     * @param id
     * @param i
     * @return
     */
    @Query("select sum(d.sumOfMoney) from SafetyProductionCostPlanDetail d where d.org.id=?1 and YEAR(d.billingDate)=?2")
    Double findSumByOrgIdAndYear(String id, int i);

    /**
     * 根据企业ID和当前时间汇总
     * @param orgId
     * @param year
     * @param date
     * @return
     */
    @Query("select sum(d.sumOfMoney) from SafetyProductionCostPlanDetail d where d.org.id=?1 and YEAR(d.billingDate)=?2 and d.billingDate<=?3")
    Double findSumByOrgIdAndDate(String orgId,int year, Date date);
    /**
     * 根据企业ID和当前时间汇总
     * @param orgId
     * @param month
     * @param date
     * @return
     */
    @Query("select sum(d.sumOfMoney) from SafetyProductionCostPlanDetail d where d.org.id=?1 and MONTH(d.billingDate)=?2 and d.billingDate<=?3")
    Double findSumByOrgIdAndMonth(String orgId,int month, Date date);

    /**
     * 根据企业ID和年份对安全生产费用按计划统计
     * @param orgId
     * @param year
     * @return 0：计划ID  1：实际花费金额
     */
    @Query(nativeQuery = true,value = "select d.plan_id,sum(d.sumOfMoney)  from m019_safety_production_cost_plan_detail d where year(d.billingDate)=?2 and d.org_id=?1 group by d.plan_id")
    List<Object[]> findByOrgIdAndYearGroupByPlanId(String orgId, int year);
    /**
     * 根据企业ID和年份对安全生产费用按计划统计
     * @param orgId
     * @param year
     * @param month
     * @return 0：计划ID  1：实际花费金额
     */
    @Query(nativeQuery = true,value = "select d.plan_id,sum(d.sumOfMoney)  from m019_safety_production_cost_plan_detail d " +
            " where year(d.billingDate)=?2 and d.org_id=?1 and month(d.billingDate)=?3 group by d.plan_id")
    List<Object[]> findByOrgIdAndDateGroupByPlanId(String orgId, int year, int month);
}
