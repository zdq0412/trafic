package com.jxqixin.trafic.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
/**
 * 安全生产费用报表
 */
public class CostTotalVo implements Serializable {
    private String id;
    /**年度*/
    private int safetyYear;
    /**本年度截止月日已投入使用的各类安全生产费用合计*/
    private Double total;
    /**上年度实际营业收入*/
    private BigDecimal lastYearActualIncome= new BigDecimal("0.00");
    /**本年度应提取的安全费用,计算：通过上年度营业收入计算，普货：1%，客运和威货：1.5%*/
    private BigDecimal currentYearCost= new BigDecimal("0.00");
    /**上年度结转安全费用*/
    private BigDecimal lastYearCarryCost= new BigDecimal("0.00");
    /**本年度实际可用安全费用，计算：本年度应提取+上年度结转*/
    private BigDecimal currentYearActualCost= new BigDecimal("0.00");
    /**本年度实际使用支出的安全费用*/
    private BigDecimal currentYearActualUsed=new BigDecimal("0.00");
    /**年度结余资金*/
    private BigDecimal costLeft = new BigDecimal("0.0");

    public BigDecimal getCurrentYearActualUsed() {
        return currentYearActualUsed;
    }

    public void setCurrentYearActualUsed(BigDecimal currentYearActualUsed) {
        this.currentYearActualUsed = currentYearActualUsed;
    }
    public BigDecimal getCostLeft() {
        return costLeft;
    }

    public void setCostLeft(BigDecimal costLeft) {
        this.costLeft = costLeft;
    }

    @JsonIgnoreProperties(value = {"safetyProductionCost"})
    private List<SafetyProductionCostPlanVo> plans;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<SafetyProductionCostPlanVo> getPlans() {
        return plans;
    }

    public void setPlans(List<SafetyProductionCostPlanVo> plans) {
        this.plans = plans;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSafetyYear() {
        return safetyYear;
    }

    public void setSafetyYear(int safetyYear) {
        this.safetyYear = safetyYear;
    }

    public BigDecimal getLastYearActualIncome() {
        return lastYearActualIncome;
    }

    public void setLastYearActualIncome(BigDecimal lastYearActualIncome) {
        this.lastYearActualIncome = lastYearActualIncome;
    }

    public BigDecimal getCurrentYearCost() {
        return currentYearCost;
    }

    public void setCurrentYearCost(BigDecimal currentYearCost) {
        this.currentYearCost = currentYearCost;
    }

    public BigDecimal getLastYearCarryCost() {
        return lastYearCarryCost;
    }

    public void setLastYearCarryCost(BigDecimal lastYearCarryCost) {
        this.lastYearCarryCost = lastYearCarryCost;
    }

    public BigDecimal getCurrentYearActualCost() {
        return currentYearActualCost;
    }

    public void setCurrentYearActualCost(BigDecimal currentYearActualCost) {
        this.currentYearActualCost = currentYearActualCost;
    }
}
