package com.jxqixin.trafic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *年度安全生产费用
 */
@Entity
@Table(name = "m019_safety_production_cost")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class SafetyProductionCost  implements Serializable {
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**年度*/
	private int safetyYear;
	/**创建日期*/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	/**上年度实际营业收入*/
	private BigDecimal lastYearActualIncome= new BigDecimal("0.00");
	/**本年度应提取的安全费用,计算：通过上年度营业收入计算，普货：1%，客运和威货：1.5%*/
	private BigDecimal currentYearCost= new BigDecimal("0.00");
	/**上年度结转安全费用*/
	private BigDecimal lastYearCarryCost= new BigDecimal("0.00");
	/**本年度实际可用安全费用，计算：本年度应提取+上年度结转*/
	private BigDecimal currentYearActualCost= new BigDecimal("0.00");
	/**费用单位*/
	private String unit="元";
	/**所属企业*/
	@ManyToOne
	@JoinColumn(name = "org_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Org org;
	/**当前年是否已填写安全生产费用使用计划*/
	private boolean isFillIn;
	/**安全生产费用下的生产计划*/
	@Transient
	@JsonIgnoreProperties(value = {"safetyProductionCost"})
	private List<SafetyProductionCostPlan> plans;

	public List<SafetyProductionCostPlan> getPlans() {
		return plans;
	}

	public void setPlans(List<SafetyProductionCostPlan> plans) {
		this.plans = plans;
	}

	public boolean isFillIn() {
		return isFillIn;
	}

	public void setFillIn(boolean fillIn) {
		isFillIn = fillIn;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}
}
