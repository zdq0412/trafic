package com.jxqixin.trafic.dto;

import java.math.BigDecimal;

/**
 *年度安全生产费用
 */
public class SafetyProductionCostDto {
	private String id;
	/**上年度实际营业收入*/
	private BigDecimal lastYearActualIncome= new BigDecimal("0.00");
	/**本年度应提取的安全费用,计算：通过上年度营业收入计算，普货：1%，客运和威货：1.5%*/
	private BigDecimal currentYearCost= new BigDecimal("0.00");
	/**上年度结转安全费用*/
	private BigDecimal lastYearCarryCost= new BigDecimal("0.00");
	/**本年度实际可用安全费用，计算：本年度应提取+上年度结转*/
	private BigDecimal currentYearActualCost= new BigDecimal("0.00");
	private String plans;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getPlans() {
		return plans;
	}

	public void setPlans(String plans) {
		this.plans = plans;
	}
}
