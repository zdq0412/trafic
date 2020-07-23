package com.jxqixin.trafic.dto;
import com.jxqixin.trafic.model.SafetyProductionCostPlan;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
/**
 *年度安全生产费用使用计划详情
 */
public class SafetyProductionCostPlanDetailDto extends PageDto{
	private String id;
	/**开票日期*/
	private String billingDate;
	/**内容摘要*/
	private String content;
	/**金额*/
	private String sumOfMoney;
	/**票据号码*/
	private String billNo;
	/**经办人*/
	private String operator;
	/**备注*/
	private String note;
	/**年度安全生产费用使用计划*/
	private String safetyProductionCostPlanId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(String billingDate) {
		this.billingDate = billingDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSumOfMoney() {
		return sumOfMoney;
	}

	public void setSumOfMoney(String sumOfMoney) {
		this.sumOfMoney = sumOfMoney;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSafetyProductionCostPlanId() {
		return safetyProductionCostPlanId;
	}

	public void setSafetyProductionCostPlanId(String safetyProductionCostPlanId) {
		this.safetyProductionCostPlanId = safetyProductionCostPlanId;
	}
}
