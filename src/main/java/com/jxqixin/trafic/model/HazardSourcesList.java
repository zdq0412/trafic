package com.jxqixin.trafic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 危险源清单
 */
@Entity
@Table(name = "m037_hazard_sources_list")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class HazardSourcesList implements Serializable {
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**危险源名称*/
	private String name;
	/**发生可能性*/
	private int happen;
	/**后果严重性*/
	private int consequence;
	/**判定准则*/
	private int criterion;
	/**安全风险等级*/
	private String riskLevel;
	/**四色标识*/
	private String fourColor;
	/**应采取的行动/控制措施*/
	private String measures;
	/**实施期限*/
	private String timeLimit;
	/**所属企业*/
	@ManyToOne
	@JoinColumn(name = "org_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private Org org;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHappen() {
		return happen;
	}

	public void setHappen(int happen) {
		this.happen = happen;
	}

	public int getConsequence() {
		return consequence;
	}

	public void setConsequence(int consequence) {
		this.consequence = consequence;
	}

	public int getCriterion() {
		return criterion;
	}

	public void setCriterion(int criterion) {
		this.criterion = criterion;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getFourColor() {
		return fourColor;
	}

	public void setFourColor(String fourColor) {
		this.fourColor = fourColor;
	}

	public String getMeasures() {
		return measures;
	}

	public void setMeasures(String measures) {
		this.measures = measures;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}
}
