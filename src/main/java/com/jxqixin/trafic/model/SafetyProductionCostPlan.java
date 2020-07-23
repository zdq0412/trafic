package com.jxqixin.trafic.model;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
/**
 *年度安全生产费用使用计划
 */
@Entity
@Table(name = "m019_safety_production_cost_plan")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class SafetyProductionCostPlan {
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**序号*/
	private String serialNo;
	/**名称*/
	private String name;
	/**计划金额*/
	private BigDecimal planCost = new BigDecimal("0.00");
	/**年度安全生产费用对象*/
	@ManyToOne
	@JoinColumn(name = "safety_production_cost_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SafetyProductionCost safetyProductionCost;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPlanCost() {
		return planCost;
	}

	public void setPlanCost(BigDecimal planCost) {
		this.planCost = planCost;
	}

	public SafetyProductionCost getSafetyProductionCost() {
		return safetyProductionCost;
	}

	public void setSafetyProductionCost(SafetyProductionCost safetyProductionCost) {
		this.safetyProductionCost = safetyProductionCost;
	}
}
