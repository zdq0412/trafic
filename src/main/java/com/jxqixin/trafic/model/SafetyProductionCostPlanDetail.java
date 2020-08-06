package com.jxqixin.trafic.model;
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
@Entity
@Table(name = "m019_safety_production_cost_plan_detail")
@GenericGenerator(name="id_gen",strategy = "uuid")
public class SafetyProductionCostPlanDetail {
	@Id
	@GeneratedValue(generator = "id_gen")
	private String id;
	/**开票日期*/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date billingDate;
	/**创建日期*/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	/**内容摘要*/
	private String content;
	/**金额*/
	private BigDecimal sumOfMoney= new BigDecimal("0.00");
	/**票据号码*/
	private String billNo;
	/**经办人*/
	private String operator;
	/**备注*/
	private String note;
	/**年度安全生产费用使用计划*/
	@ManyToOne
	@JoinColumn(name = "plan_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@NotFound(action = NotFoundAction.IGNORE)
	private SafetyProductionCostPlan safetyProductionCostPlan;
	/**所属企业*/
	@ManyToOne
	@JoinColumn(name = "org_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Org org;
	/**文件访问路径*/
	private String url;
	/**文件存储路径*/
	private String realPath;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public Org getOrg() {
		return org;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public SafetyProductionCostPlan getSafetyProductionCostPlan() {
		return safetyProductionCostPlan;
	}

	public void setSafetyProductionCostPlan(SafetyProductionCostPlan safetyProductionCostPlan) {
		this.safetyProductionCostPlan = safetyProductionCostPlan;
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

	public Date getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}

	public BigDecimal getSumOfMoney() {
		return sumOfMoney;
	}

	public void setSumOfMoney(BigDecimal sumOfMoney) {
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
}
