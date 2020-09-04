package com.jxqixin.trafic.dto;

import java.io.Serializable;
/**
 * 风险等级
 */
public class RiskLevelDto extends PageDto implements Serializable {
	private String id;
	/**名称*/
	private String name;
	/**备注*/
	private String note;
	/**风险等级颜色*/
	private String color;
	/**风险值范围上限*/
	private Integer upperLimit;
	/**风险值范围下限*/
	private Integer lowerLimit;
	/**实施期限*/
	private String timeLimit;
	/**控制措施*/
	private String measure;

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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(Integer upperLimit) {
		this.upperLimit = upperLimit;
	}

	public Integer getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(Integer lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}
}
